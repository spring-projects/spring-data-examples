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
package example.springdata.mongodb.reactive;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.Process;
import example.springdata.mongodb.State;
import example.springdata.mongodb.util.EmbeddedMongo;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

/**
 * Test showing MongoDB Transaction usage through a reactive API.
 *
 * @author Christoph Strobl
 * @currentRead The Core - Peter V. Brett
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class ReactiveTransitionServiceTests {

	public static @ClassRule EmbeddedMongo replSet = EmbeddedMongo.replSet().configure();

	@Autowired ReactiveTransitionService transitionService;
	@Autowired MongoClient client;

	static final String DB_NAME = "spring-data-reactive-tx-examples";

	@Configuration
	@ComponentScan
	@EnableReactiveMongoRepositories
	static class Config extends AbstractReactiveMongoConfiguration {

		@Bean
		@Override
		public MongoClient reactiveMongoClient() {
			return MongoClients.create(replSet.getConnectionString());
		}

		@Override
		protected String getDatabaseName() {
			return DB_NAME;
		}
	}

	@Test
	public void reactiveTxCommitRollback() {

		for (var i = 0; i < 10; i++) {
			transitionService.newProcess() //
					.map(Process::id) //
					.flatMap(transitionService::run) //
					.onErrorReturn(-1).as(StepVerifier::create) //
					.consumeNextWith(val -> {}) //
					.verifyComplete();
		}

		Flux.from(client.getDatabase(DB_NAME).getCollection("processes").find(new Document())) //
				.buffer(10) //
				.as(StepVerifier::create) //
				.consumeNextWith(list -> {

					for (var document : list) {

						System.out.println("document: " + document);

						if (document.getInteger("_id") % 3 == 0) {
							assertThat(document.getString("state")).isEqualTo(State.CREATED.toString());
						} else {
							assertThat(document.getString("state")).isEqualTo(State.DONE.toString());
						}
					}

				}) //
				.verifyComplete();
	}
}
