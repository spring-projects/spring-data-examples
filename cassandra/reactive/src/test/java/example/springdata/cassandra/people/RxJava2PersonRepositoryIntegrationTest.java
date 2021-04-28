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
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;

/**
 * Integration test for {@link RxJava2PersonRepository} using RxJava1 types. Note that
 * {@link ReactiveCassandraOperations} is only available using Project Reactor types as the native Template API
 * implementation does not come in multiple reactive flavors.
 *
 * @author Mark Paluch
 */
@CassandraKeyspace
@SpringBootTest
public class RxJava2PersonRepositoryIntegrationTest {


	@Autowired RxJava2PersonRepository repository;
	@Autowired ReactiveCassandraOperations operations;

	@BeforeEach
	public void setUp() throws Exception {

		var deleteAll = repository.deleteAll();

		var save = repository.saveAll(Flowable.just(new Person("Walter", "White", 50), //
				new Person("Skyler", "White", 45), //
				new Person("Saul", "Goodman", 42), //
				new Person("Jesse", "Pinkman", 27)));

		deleteAll.andThen(save).test().await().assertNoErrors();
	}

	/**
	 * This sample performs a count, inserts data and performs a count again using reactive operator chaining. It prints
	 * the two counts ({@code 4} and {@code 6}) to the console.
	 */
	@Test
	public void shouldInsertAndCountData() {


		repository.count() //
				.doOnSuccess(System.out::println) //
				.toFlowable() //
				.switchMap(count -> repository.saveAll(Flowable.just(new Person("Hank", "Schrader", 43), //
						new Person("Mike", "Ehrmantraut", 62)))) //
				.lastElement() //
				.toSingle() //
				.flatMap(v -> repository.count()) //
				.doOnSuccess(System.out::println) //
				.test() //
				.awaitCount(1) //
				.assertValue(6L) //
				.assertNoErrors() //
				.awaitTerminalEvent();
	}

	/**
	 * Result set {@link com.datastax.driver.core.Row}s are converted to entities as they are emitted. Reactive pull and
	 * prefetch define the amount of fetched records.
	 */
	@Test
	public void shouldPerformConversionBeforeResultProcessing() {

		repository.findAll() //
				.doOnNext(System.out::println) //
				.test() //
				.awaitCount(4) //
				.assertNoErrors() //
				.awaitTerminalEvent();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	public void shouldQueryDataWithQueryDerivation() {

		repository.findByLastname("White") //
				.test() //
				.awaitCount(2) //
				.assertNoErrors() //
				.awaitTerminalEvent();
	}

	/**
	 * Fetch data using a string query.
	 */
	@Test
	public void shouldQueryDataWithStringQuery() {

		repository.findByFirstnameAndLastname("Walter", "White") //
				.test() //
				.awaitCount(1) //
				.assertNoErrors() //
				.awaitTerminalEvent();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	public void shouldQueryDataWithDeferredQueryDerivation() {

		repository.findByLastname(Single.just("White")) //
				.test() //
				.awaitCount(2) //
				.assertNoErrors() //
				.awaitTerminalEvent();
	}

	/**
	 * Fetch data using query derivation and deferred parameter resolution.
	 */
	@Test
	public void shouldQueryDataWithMixedDeferredQueryDerivation() {

		repository.findByFirstnameAndLastname(Single.just("Walter"), "White") //
				.test() //
				.awaitCount(1) //
				.assertNoErrors() //
				.awaitTerminalEvent();
	}
}
