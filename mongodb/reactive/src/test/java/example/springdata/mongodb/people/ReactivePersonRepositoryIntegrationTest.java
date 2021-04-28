/*
 * Copyright 2015-2021 the original author or authors.
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
package example.springdata.mongodb.people;

import static org.assertj.core.api.Assertions.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

/**
 * Integration test for {@link ReactivePersonRepository} using Project Reactor types and operators.
 *
 * @author Mark Paluch
 */
@DataMongoTest
class ReactivePersonRepositoryIntegrationTest {

	@Autowired ReactivePersonRepository repository;
	@Autowired ReactiveMongoOperations operations;

	@BeforeEach
	void setUp() {

		var recreateCollection = operations.collectionExists(Person.class) //
				.flatMap(exists -> exists ? operations.dropCollection(Person.class) : Mono.just(exists)) //
				.then(operations.createCollection(Person.class, CollectionOptions.empty() //
						.size(1024 * 1024) //
						.maxDocuments(100) //
						.capped()));

		recreateCollection.as(StepVerifier::create).expectNextCount(1).verifyComplete();

		var insertAll = operations.insertAll(Flux.just(new Person("Walter", "White", 50), //
						new Person("Skyler", "White", 45), //
						new Person("Saul", "Goodman", 42), //
				new Person("Jesse", "Pinkman", 27)).collectList());

		insertAll.as(StepVerifier::create).expectNextCount(4).verifyComplete();
	}

	/**
	 * This sample performs a count, inserts data and performs a count again using reactive operator chaining. It prints
	 * the two counts ({@code 4} and {@code 6}) to the console.
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

		saveAndCount.as(StepVerifier::create).expectNext(6L).verifyComplete();
	}

	/**
	 * Note that the all object conversions are performed before the results are printed to the console.
	 */
	@Test
	void shouldPerformConversionBeforeResultProcessing() {

		repository.findAll().doOnNext(System.out::println) //
				.as(StepVerifier::create) //
				.expectNextCount(4) //
				.verifyComplete();
	}

	/**
	 * A tailable cursor streams data using {@link Flux} as it arrives inside the capped collection.
	 */
	@Test
	void shouldStreamDataWithTailableCursor() throws Exception {

		Queue<Person> people = new ConcurrentLinkedQueue<>();

		var disposable = repository.findWithTailableCursorBy() //
				.doOnNext(System.out::println) //
				.doOnNext(people::add) //
				.doOnComplete(() -> System.out.println("Complete")) //
				.doOnTerminate(() -> System.out.println("Terminated")) //
				.subscribe();

		Thread.sleep(100);

		repository.save(new Person("Tuco", "Salamanca", 33)) //
				.as(StepVerifier::create) //
				.expectNextCount(1) //
				.verifyComplete();
		Thread.sleep(100);

		repository.save(new Person("Mike", "Ehrmantraut", 62)) //
				.as(StepVerifier::create) //
				.expectNextCount(1) //
				.verifyComplete();
		Thread.sleep(100);

		disposable.dispose();

		repository.save(new Person("Gus", "Fring", 53)) //
				.as(StepVerifier::create) //
				.expectNextCount(1) //
				.verifyComplete();
		Thread.sleep(100);

		assertThat(people).hasSize(6);
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	void shouldQueryDataWithQueryDerivation() {
		repository.findByLastname("White").as(StepVerifier::create).expectNextCount(2).verifyComplete();
	}

	/**
	 * Fetch data using a string query.
	 */
	@Test
	void shouldQueryDataWithStringQuery() {
		repository.findByFirstnameAndLastname("Walter", "White").as(StepVerifier::create).expectNextCount(1)
				.verifyComplete();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	void shouldQueryDataWithDeferredQueryDerivation() {
		repository.findByLastname(Mono.just("White")).as(StepVerifier::create).expectNextCount(2).verifyComplete();
	}

	/**
	 * Fetch data using query derivation and deferred parameter resolution.
	 */
	@Test
	void shouldQueryDataWithMixedDeferredQueryDerivation() {

		repository.findByFirstnameAndLastname(Mono.just("Walter"), "White").as(StepVerifier::create) //
				.expectNextCount(1) //
				.verifyComplete();
	}
}
