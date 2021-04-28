/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.cassandra.people;

import example.springdata.cassandra.util.CassandraKeyspace;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test for {@link ReactivePersonRepository} using Project Reactor types and operators.
 *
 * @author Mark Paluch
 */
@SpringBootTest
@CassandraKeyspace
class ReactivePersonRepositoryIntegrationTest {

	@Autowired ReactivePersonRepository repository;

	/**
	 * Clear table and insert some rows.
	 */
	@BeforeEach
	void setUp() {

		var deleteAndInsert = repository.deleteAll() //
				.thenMany(repository.saveAll(Flux.just(new Person("Walter", "White", 50), //
						new Person("Skyler", "White", 45), //
						new Person("Saul", "Goodman", 42), //
						new Person("Jesse", "Pinkman", 27))));

		StepVerifier.create(deleteAndInsert).expectNextCount(4).verifyComplete();
	}

	/**
	 * This sample performs a count, inserts data and performs a count again using reactive operator chaining.
	 */
	@Test
	void shouldInsertAndCountData() {

		var saveAndCount = repository.count() //
				.doOnNext(System.out::println) //
				.thenMany(repository.saveAll(Flux.just(new Person("Hank", "Schrader", 43), //
						new Person("Mike", "Ehrmantraut", 62)))) //
				.last() //
				.flatMap(v -> repository.count()) //
				.doOnNext(System.out::println);

		StepVerifier.create(saveAndCount).expectNext(6L).verifyComplete();
	}

	/**
	 * Result set {@link com.datastax.driver.core.Row}s are converted to entities as they are emitted. Reactive pull and
	 * prefetch define the amount of fetched records.
	 */
	@Test
	void shouldPerformConversionBeforeResultProcessing() {

		StepVerifier.create(repository.findAll().doOnNext(System.out::println)) //
				.expectNextCount(4) //
				.verifyComplete();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	void shouldQueryDataWithQueryDerivation() {
		StepVerifier.create(repository.findByLastname("White")).expectNextCount(2).verifyComplete();
	}

	/**
	 * Fetch data using a string query.
	 */
	@Test
	void shouldQueryDataWithStringQuery() {
		StepVerifier.create(repository.findByFirstnameInAndLastname("Walter", "White")).expectNextCount(1).verifyComplete();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	void shouldQueryDataWithDeferredQueryDerivation() {
		StepVerifier.create(repository.findByLastname(Mono.just("White"))).expectNextCount(2).verifyComplete();
	}

	/**
	 * Fetch data using query derivation and deferred parameter resolution.
	 */
	@Test
	void shouldQueryDataWithMixedDeferredQueryDerivation() {

		StepVerifier.create(repository.findByFirstnameAndLastname(Mono.just("Walter"), "White")) //
				.expectNextCount(1) //
				.verifyComplete();
	}

}
