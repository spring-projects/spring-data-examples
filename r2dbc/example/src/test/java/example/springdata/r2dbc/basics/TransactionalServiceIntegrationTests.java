/*
 * Copyright 2019 the original author or authors.
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

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for {@link TransactionalService}.
 *
 * @author Oliver Drotbohm
 * @soundtrack Shame - Tedeschi Trucks Band (Signs)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfrastructureConfiguration.class)
public class TransactionalServiceIntegrationTests {

	@Autowired TransactionalService service;
	@Autowired CustomerRepository repository;
	@Autowired DatabaseClient database;

	@Before
	public void setUp() {

		Hooks.onOperatorDebug();

		List<String> statements = Arrays.asList(//
				"DROP TABLE IF EXISTS customer;",
				"CREATE TABLE customer ( id SERIAL PRIMARY KEY, firstname VARCHAR(100) NOT NULL, lastname VARCHAR(100) NOT NULL);");

		statements.forEach(it -> database.execute(it) //
				.fetch() //
				.rowsUpdated() //
				.as(StepVerifier::create) //
				.expectNextCount(1) //
				.verifyComplete());
	}

	@Test // #500
	public void exceptionTriggersRollback() {

		service.save(new Customer(null, "Dave", "Matthews")) //
				.as(StepVerifier::create) //
				.expectError() // Error because of the exception triggered within the service
				.verify();

		// No data inserted due to rollback
		repository.findByLastname("Matthews") //
				.as(StepVerifier::create) //
				.verifyComplete();
	}

	@Test // #500
	public void insertsDataTransactionally() {

		service.save(new Customer(null, "Carter", "Beauford")) //
				.as(StepVerifier::create) //
				.expectNextMatches(Customer::hasId) //
				.verifyComplete();

		// Data inserted due to commit
		repository.findByLastname("Beauford") //
				.as(StepVerifier::create) //
				.expectNextMatches(Customer::hasId) //
				.verifyComplete();
	}
}
