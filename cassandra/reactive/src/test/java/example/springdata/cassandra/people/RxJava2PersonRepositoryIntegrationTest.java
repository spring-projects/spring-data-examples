/*
 * Copyright 2016-2017 the original author or authors.
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
package example.springdata.cassandra.people;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test for {@link RxJava2PersonRepository} using RxJava1 types. Note that
 * {@link ReactiveCassandraOperations} is only available using Project Reactor types as the native Template API
 * implementation does not come in multiple reactive flavors.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RxJava2PersonRepositoryIntegrationTest {

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost();

	@Autowired RxJava2PersonRepository repository;
	@Autowired ReactiveCassandraOperations operations;

	@Before
	public void setUp() throws Exception {

		Completable deleteAll = repository.deleteAll();

		Flowable<Person> save = repository.saveAll(Flowable.just(new Person("Walter", "White", 50), //
				new Person("Skyler", "White", 45), //
				new Person("Saul", "Goodman", 42), //
				new Person("Jesse", "Pinkman", 27)));

		deleteAll.andThen(save).blockingLast();
	}

	/**
	 * This sample performs a count, inserts data and performs a count again using reactive operator chaining.
	 */
	@Test
	public void shouldInsertAndCountData() throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		repository.count() //
				.doOnSuccess(System.out::println) //
				.toFlowable() //
				.switchMap(count -> repository.saveAll(Flowable.just(new Person("Hank", "Schrader", 43), //
						new Person("Mike", "Ehrmantraut", 62)))) //
				.lastElement() //
				.toSingle() //
				.flatMap(v -> repository.count()) //
				.doOnSuccess(System.out::println) //
				.doAfterTerminate(countDownLatch::countDown) //
				.subscribe();

		countDownLatch.await();
	}

	/**
	 * Result set {@link com.datastax.driver.core.Row}s are converted to entities as they are emitted. Reactive pull and
	 * prefetch define the amount of fetched records.
	 */
	@Test
	public void shouldPerformConversionBeforeResultProcessing() throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		repository.findAll() //
				.doOnNext(System.out::println) //
				.doOnEach(it -> countDownLatch.countDown()) //
				.doOnError(throwable -> countDownLatch.countDown()) //
				.subscribe();

		countDownLatch.await();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	public void shouldQueryDataWithQueryDerivation() {

		List<Person> whites = repository.findByLastname("White") //
				.toList() //
				.blockingGet();

		assertThat(whites).hasSize(2);
	}

	/**
	 * Fetch data using a string query.
	 */
	@Test
	public void shouldQueryDataWithStringQuery() {

		Person heisenberg = repository.findByFirstnameAndLastname("Walter", "White") //
				.blockingGet();

		assertThat(heisenberg).isNotNull();
	}

	/**
	 * Fetch data using query derivation.
	 */
	@Test
	public void shouldQueryDataWithDeferredQueryDerivation() {

		List<Person> whites = repository.findByLastname(Single.just("White")) //
				.toList() //
				.blockingGet();

		assertThat(whites).hasSize(2);
	}

	/**
	 * Fetch data using query derivation and deferred parameter resolution.
	 */
	@Test
	public void shouldQueryDataWithMixedDeferredQueryDerivation() {

		Person heisenberg = repository.findByFirstnameAndLastname(Single.just("Walter"), "White") //
				.blockingGet();

		assertThat(heisenberg).isNotNull();
	}
}
