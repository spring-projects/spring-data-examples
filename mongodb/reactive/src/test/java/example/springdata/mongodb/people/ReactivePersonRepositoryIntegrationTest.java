/*
 * Copyright 2015-2016 the original author or authors.
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
package example.springdata.mongodb.people;

import static org.assertj.core.api.Assertions.*;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test for {@link ReactivePersonRepository} using Project Reactor types and operators.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactivePersonRepositoryIntegrationTest {

	@Autowired ReactivePersonRepository repository;
	@Autowired ReactiveMongoOperations operations;

	@Before
	public void setUp() {

		operations.collectionExists(Person.class) //
				.flatMap(exists -> exists ? operations.dropCollection(Person.class) : Mono.just(exists)) //
				.flatMap(o -> operations.createCollection(Person.class, new CollectionOptions(1024 * 1024, 100, true))) //
				.then() //
				.block();

		repository
				.saveAll(Flux.just(new Person("Walter", "White", 50), //
						new Person("Skyler", "White", 45), //
						new Person("Saul", "Goodman", 42), //
						new Person("Jesse", "Pinkman", 27))) //
				.then() //
				.block();

	}

	/**
	 * This sample performs a count, inserts data and performs a count again using reactive operator chaining.
	 */
	@Test
	public void shouldInsertAndCountData() throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		repository.count() //
				.doOnNext(System.out::println) //
				.thenMany(repository.saveAll(Flux.just(new Person("Hank", "Schrader", 43), //
						new Person("Mike", "Ehrmantraut", 62)))) //
				.last() //
				.flatMap(v -> repository.count()) //
				.doOnNext(System.out::println) //
				.doOnSuccess(it -> countDownLatch.countDown()) //
				.doOnError(throwable -> countDownLatch.countDown()) //
				.subscribe();

		countDownLatch.await();
	}

	/**
	 * Note that the all object conversions are performed before the results are printed to the console.
	 */
	@Test
	public void shouldPerformConversionBeforeResultProcessing() throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		repository.findAll() //
				.doOnNext(System.out::println) //
				.doOnComplete(countDownLatch::countDown) //
				.doOnError(throwable -> countDownLatch.countDown()) //
				.subscribe();

		countDownLatch.await();
	}

	/**
	 * A tailable cursor streams data using {@link Flux} as it arrives inside the capped collection.
	 */
	@Test
	public void shouldStreamDataWithTailableCursor() throws Exception {

		Disposable disposable = repository.findWithTailableCursorBy() //
				.doOnNext(System.out::println) //
				.doOnComplete(() -> System.out.println("Complete")) //
				.doOnTerminate(() -> System.out.println("Terminated")) //
				.subscribe();

		Thread.sleep(100);

		repository.save(new Person("Tuco", "Salamanca", 33)).subscribe();
		Thread.sleep(100);

		repository.save(new Person("Mike", "Ehrmantraut", 62)).subscribe();
		Thread.sleep(100);

		disposable.dispose();

		repository.save(new Person("Gus", "Fring", 53)).subscribe();
		Thread.sleep(100);
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	public void shouldQueryDataWithQueryDerivation() {

		List<Person> whites = repository.findByLastname("White") //
				.collectList() //
				.block();

		assertThat(whites).hasSize(2);
	}

	/**
	 * Fetch data using a string query.
	 */
	@Test
	public void shouldQueryDataWithStringQuery() {

		Person heisenberg = repository.findByFirstnameAndLastname("Walter", "White") //
				.block();

		assertThat(heisenberg).isNotNull();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	public void shouldQueryDataWithDeferredQueryDerivation() {

		List<Person> whites = repository.findByLastname(Mono.just("White")) //
				.collectList() //
				.block();

		assertThat(whites).hasSize(2);
	}

	/**
	 * Fetch data using query derivation and deferred parameter resolution.
	 */
	@Test
	public void shouldQueryDataWithMixedDeferredQueryDerivation() {

		Person heisenberg = repository.findByFirstnameAndLastname(Mono.just("Walter"), "White") //
				.block();

		assertThat(heisenberg).isNotNull();
	}
}
