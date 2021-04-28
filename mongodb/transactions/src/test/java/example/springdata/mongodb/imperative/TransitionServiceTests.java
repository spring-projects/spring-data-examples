/*
 * Copyright 2018-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.mongodb.imperative;

import example.springdata.mongodb.Process;
import example.springdata.mongodb.State;
import example.springdata.mongodb.util.EmbeddedMongo;

import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

/**
 * Test showing MongoDB Transaction usage through a synchronous (imperative) API using Spring's managed transactions.
 *
 * @author Christoph Strobl
 * @currentRead The Core - Peter V. Brett
 * @see org.springframework.transaction.annotation.Transactional
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class TransitionServiceTests {

	public static @ClassRule EmbeddedMongo replSet = EmbeddedMongo.replSet().configure();

	static final String DB_NAME = "spring-data-tx-examples";

	@Autowired TransitionService transitionService;
	@Autowired MongoClient client;

	@Configuration
	@ComponentScan
	@EnableMongoRepositories
	@EnableTransactionManagement
	static class Config extends AbstractMongoClientConfiguration {

		@Bean
		PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
			return new MongoTransactionManager(dbFactory);
		}

		@Override
		@Bean
		public MongoClient mongoClient() {
			return replSet.getMongoClient();
		}

		@Override
		protected String getDatabaseName() {
			return DB_NAME;
		}
	}

	@Test
	public void txCommitRollback() {

		for (var i = 0; i < 10; i++) {

			var process = transitionService.newProcess();

			try {

				transitionService.run(process.id());
				Assertions.assertThat(stateInDb(process)).isEqualTo(State.DONE);
			} catch (IllegalStateException e) {
				Assertions.assertThat(stateInDb(process)).isEqualTo(State.CREATED);
			}
		}

		client.getDatabase(DB_NAME).getCollection("processes").find(new Document())
				.forEach((Consumer<? super Document>) System.out::println);
	}

	State stateInDb(Process process) {

		return State.valueOf(client.getDatabase(DB_NAME).getCollection("processes").find(Filters.eq("_id", process.id()))
				.projection(Projections.include("state")).first().get("state", String.class));
	}

}
