/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.r2dbc.basics;

import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;

/**
 * @author Oliver Gierke
 */
@SpringBootTest(classes = InfrastructureConfiguration.class)
class CustomerRepositoryIntegrationTests {

	@Autowired CustomerRepository customers;
	@Autowired DatabaseClient database;

	@BeforeEach
	void setUp() {

		Hooks.onOperatorDebug();

		var statements = Arrays.asList(//
				"DROP TABLE IF EXISTS customer;",
				"CREATE TABLE customer ( id SERIAL PRIMARY KEY, firstname VARCHAR(100) NOT NULL, lastname VARCHAR(100) NOT NULL);");

		statements.forEach(it -> database.sql(it) //
				.fetch() //
				.rowsUpdated() //
				.as(StepVerifier::create) //
				.expectNextCount(1) //
				.verifyComplete());
	}

	@Test
	void executesFindAll() throws IOException {

		var dave = new Customer(null, "Dave", "Matthews");
		var carter = new Customer(null, "Carter", "Beauford");

		insertCustomers(dave, carter);

		customers.findAll() //
				.as(StepVerifier::create) //
				.expectNext(dave) //
				.expectNext(carter) //
				.verifyComplete();
	}

	@Test
	void executesAnnotatedQuery() throws IOException {

		var dave = new Customer(null, "Dave", "Matthews");
		var carter = new Customer(null, "Carter", "Beauford");

		insertCustomers(dave, carter);

		customers.findByLastname("Matthews") //
				.as(StepVerifier::create) //
				.expectNext(dave) //
				.verifyComplete();
	}

	private void insertCustomers(Customer... customers) {

		this.customers.saveAll(Arrays.asList(customers))//
				.as(StepVerifier::create) //
				.expectNextCount(2) //
				.verifyComplete();
	}
}
