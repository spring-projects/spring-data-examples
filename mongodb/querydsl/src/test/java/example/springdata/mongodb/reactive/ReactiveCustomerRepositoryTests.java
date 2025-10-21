/*
 * Copyright 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.mongodb.reactive;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.Customer;
import example.springdata.mongodb.QCustomer;
import example.springdata.mongodb.util.MongoContainers;
import reactor.test.StepVerifier;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;

/**
 * @author Christoph Strobl
 */
@Testcontainers
@DataMongoTest
class ReactiveCustomerRepositoryTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired ReactiveCustomerQuerydslRepository repository;
	@Autowired MongoOperations operations;

	private Customer dave, oliver, carter;

	@BeforeEach
	void setUp() {

		repository.deleteAll().as(StepVerifier::create).verifyComplete();

		dave = new Customer("Dave", "Matthews");
		oliver = new Customer("Oliver August", "Matthews");
		carter = new Customer("Carter", "Beauford");

		repository.saveAll(Arrays.asList(dave, oliver, carter)).then().as(StepVerifier::create).verifyComplete();
	}

	@Test
	void findAllByPredicate() {

		repository.findAll(QCustomer.customer.lastname.eq("Matthews")) //
				.collectList() //
				.as(StepVerifier::create) //
				.assertNext(it -> assertThat(it).containsExactlyInAnyOrder(dave, oliver)) //
				.verifyComplete();
	}

}
