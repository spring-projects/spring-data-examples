/*
 * Copyright 2019-2021 the original author or authors.
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

import com.mongodb.reactivestreams.client.MongoClients;
import example.springdata.mongodb.Process;
import com.mongodb.reactivestreams.client.MongoClient;
import example.springdata.mongodb.State;
import example.springdata.mongodb.util.MongoContainers;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Test showing MongoDB Transaction usage through a reactive API.
 *
 * @author Christoph Strobl
 * @currentRead The Core - Peter V. Brett
 */
@Testcontainers
@ExtendWith(SpringExtension.class)
public class ReactiveManagedTransitionServiceTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired ReactiveManagedTransitionService managedTransitionService;
	@Autowired MongoClient client;

	static final String DB_NAME = "spring-data-reactive-tx-examples";

	@Configuration
	@ComponentScan
	@EnableReactiveMongoRepositories
	@EnableTransactionManagement
	static class Config extends AbstractReactiveMongoConfiguration {

		@Bean
		ReactiveTransactionManager transactionManager(ReactiveMongoDatabaseFactory dbFactory) {
			return new ReactiveMongoTransactionManager(dbFactory);
		}

		@Bean
		@Override
		public MongoClient reactiveMongoClient() {
			return MongoClients.create(mongoDBContainer.getReplicaSetUrl());
		}

		@Override
		protected String getDatabaseName() {
			return DB_NAME;
		}
	}

	@Test
	public void reactiveTxCommitRollback() {

		for (var i = 0; i < 10; i++) {
			managedTransitionService.newProcess() //
					.map(Process::id) //
					.flatMap(managedTransitionService::run) //
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
