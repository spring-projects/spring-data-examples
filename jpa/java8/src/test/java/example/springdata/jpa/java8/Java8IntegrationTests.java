/*
 * Copyright 2013-2015 the original author or authors.
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
package example.springdata.jpa.java8;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test to show the usage of Java 8 date time APIs with Spring Data JPA auditing.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AuditingConfiguration.class)
@Transactional
@Slf4j
public class Java8IntegrationTests {

	@Autowired CustomerRepository repository;

	@Test
	public void providesFindOneWithOptional() {

		Customer carter = repository.save(new Customer("Carter", "Beauford"));

		assertThat(repository.findOne(carter.id).isPresent(), is(true));
		assertThat(repository.findOne(carter.id + 1), is(Optional.<Customer> empty()));
	}

	@Test
	public void auditingSetsJdk8DateTimeTypes() {

		Customer customer = repository.save(new Customer("Dave", "Matthews"));

		assertThat(customer.createdDate, is(notNullValue()));
		assertThat(customer.modifiedDate, is(notNullValue()));
	}

	@Test
	public void invokesDefaultMethod() {

		Customer customer = repository.save(new Customer("Dave", "Matthews"));
		Optional<Customer> result = repository.findByLastname(customer);

		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), is(customer));
	}

	/**
	 * Streaming data from the store by using a repository method that returns a {@link Stream}. Note, that since the
	 * resulting {@link Stream} contains state it needs to be closed explicitly after use!
	 */
	@Test
	public void useJava8StreamsWithCustomQuery() {

		Customer customer1 = repository.save(new Customer("Customer1", "Foo"));
		Customer customer2 = repository.save(new Customer("Customer2", "Bar"));

		try (Stream<Customer> stream = repository.streamAllCustomers()) {
			assertThat(stream.collect(Collectors.toList()), hasItems(customer1, customer2));
		}
	}

	/**
	 * Streaming data from the store by using a repository method that returns a {@link Stream} with a derived query.
	 * Note, that since the resulting {@link Stream} contains state it needs to be closed explicitly after use!
	 */
	@Test
	public void useJava8StreamsWithDerivedQuery() {

		Customer customer1 = repository.save(new Customer("Customer1", "Foo"));
		Customer customer2 = repository.save(new Customer("Customer2", "Bar"));

		try (Stream<Customer> stream = repository.findAllByLastnameIsNotNull()) {
			assertThat(stream.collect(Collectors.toList()), hasItems(customer1, customer2));
		}
	}

	/**
	 * Here we demonstrate the usage of {@link CompletableFuture} as a result wrapper for asynchronous repository query
	 * methods. Note, that we need to disable the surrounding transaction to be able to asynchronously read the written
	 * data from from another thread within the same test method.
	 */
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void supportsCompletableFuturesAsReturnTypeWrapper() throws Exception {

		repository.save(new Customer("Customer1", "Foo"));
		repository.save(new Customer("Customer2", "Bar"));

		CompletableFuture<Void> future = repository.readAllBy().thenAccept(customers -> {

			assertThat(customers, hasSize(2));
			customers.forEach(customer -> log.info(customer.toString()));
			log.info("Completed!");
		});

		while (!future.isDone()) {
			log.info("Waiting for the CompletableFuture to finish...");
			TimeUnit.MILLISECONDS.sleep(500);
		}

		future.get();

		log.info("Done!");
	}
}
