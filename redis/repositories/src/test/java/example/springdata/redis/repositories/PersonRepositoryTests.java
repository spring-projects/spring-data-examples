/*
 * Copyright 2016-2018 the original author or authors.
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
package example.springdata.redis.repositories;

import static org.assertj.core.api.Assertions.*;

import example.springdata.redis.test.util.EmbeddedRedisServer;
import example.springdata.redis.test.util.RequiresRedisServer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.index.GeoIndexed;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTests {

	/**
	 * We need to have a Redis server instance available. <br />
	 * 1) Start/Stop an embedded instance or reuse an already running local installation <br />
	 * 2) Ignore tests if startup failed and no server running locally.
	 */
	public static @ClassRule RuleChain rules = RuleChain
			.outerRule(EmbeddedRedisServer.runningAt(6379).suppressExceptions())
			.around(RequiresRedisServer.onLocalhost().atLeast("3.2"));

	/** {@link Charset} for String conversion **/
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Autowired RedisOperations<Object, Object> operations;
	@Autowired PersonRepository repository;

	/*
	 * Set of test users
	 */
	Person eddard = new Person("eddard", "stark", Gender.MALE);
	Person robb = new Person("robb", "stark", Gender.MALE);
	Person sansa = new Person("sansa", "stark", Gender.FEMALE);
	Person arya = new Person("arya", "stark", Gender.FEMALE);
	Person bran = new Person("bran", "stark", Gender.MALE);
	Person rickon = new Person("rickon", "stark", Gender.MALE);
	Person jon = new Person("jon", "snow", Gender.MALE);

	@Before
	@After
	public void setUp() {

		operations.execute((RedisConnection connection) -> {
			connection.flushDb();
			return "OK";
		});
	}

	/**
	 * Save a single entity and verify that a key for the given keyspace/prefix exists. <br />
	 * Print out the hash structure within Redis.
	 */
	@Test
	public void saveSingleEntity() {

		repository.save(eddard);

		assertThat(operations
				.execute((RedisConnection connection) -> connection.exists(("persons:" + eddard.getId()).getBytes(CHARSET))))
						.isTrue();
	}

	/**
	 * Find entity by a single {@link Indexed} property value.
	 */
	@Test
	public void findBySingleProperty() {

		flushTestUsers();

		List<Person> starks = repository.findByLastname(eddard.getLastname());

		assertThat(starks).contains(eddard, robb, sansa, arya, bran, rickon).doesNotContain(jon);
	}

	/**
	 * Find entities by multiple {@link Indexed} properties using {@literal AND}.
	 */
	@Test
	public void findByMultipleProperties() {

		flushTestUsers();

		List<Person> aryaStark = repository.findByFirstnameAndLastname(arya.getFirstname(), arya.getLastname());

		assertThat(aryaStark).containsOnly(arya);
	}

	/**
	 * Find entities by multiple {@link Indexed} properties using {@literal OR}.
	 */
	@Test
	public void findByMultiplePropertiesUsingOr() {

		flushTestUsers();

		List<Person> aryaAndJon = repository.findByFirstnameOrLastname(arya.getFirstname(), jon.getLastname());

		assertThat(aryaAndJon).containsOnly(arya, jon);
	}

	/**
	 * Find entities by {@link Example Query by Example}.
	 */
	@Test
	public void findByQueryByExample() {

		flushTestUsers();

		Example<Person> example = Example.of(new Person(null, "stark", null));

		Iterable<Person> starks = repository.findAll(example);

		assertThat(starks).contains(arya, eddard).doesNotContain(jon);
	}

	/**
	 * Find entities in range defined by {@link Pageable}.
	 */
	@Test
	public void findByReturningPage() {

		flushTestUsers();

		Page<Person> page1 = repository.findPersonByLastname(eddard.getLastname(), PageRequest.of(0, 5));

		assertThat(page1.getNumberOfElements()).isEqualTo(5);
		assertThat(page1.getTotalElements()).isEqualTo(6);

		Page<Person> page2 = repository.findPersonByLastname(eddard.getLastname(), PageRequest.of(1, 5));

		assertThat(page2.getNumberOfElements()).isEqualTo(1);
		assertThat(page2.getTotalElements()).isEqualTo(6);
	}

	/**
	 * Find entity by a single {@link Indexed} property on an embedded entity.
	 */
	@Test
	public void findByEmbeddedProperty() {

		Address winterfell = new Address();
		winterfell.setCountry("the north");
		winterfell.setCity("winterfell");

		eddard.setAddress(winterfell);

		flushTestUsers();

		List<Person> eddardStark = repository.findByAddress_City(winterfell.getCity());

		assertThat(eddardStark).containsOnly(eddard);
	}

	/**
	 * Find entity by a {@link GeoIndexed} property on an embedded entity.
	 */
	@Test
	public void findByGeoLocationProperty() {

		Address winterfell = new Address();
		winterfell.setCountry("the north");
		winterfell.setCity("winterfell");
		winterfell.setLocation(new Point(52.9541053, -1.2401016));

		eddard.setAddress(winterfell);

		Address casterlystein = new Address();
		casterlystein.setCountry("Westerland");
		casterlystein.setCity("Casterlystein");
		casterlystein.setLocation(new Point(51.5287352, -0.3817819));

		robb.setAddress(casterlystein);

		flushTestUsers();

		Circle innerCircle = new Circle(new Point(51.8911912, -0.4979756), new Distance(50, Metrics.KILOMETERS));
		List<Person> eddardStark = repository.findByAddress_LocationWithin(innerCircle);

		assertThat(eddardStark).containsOnly(robb);

		Circle biggerCircle = new Circle(new Point(51.8911912, -0.4979756), new Distance(200, Metrics.KILOMETERS));
		List<Person> eddardAndRobbStark = repository.findByAddress_LocationWithin(biggerCircle);

		assertThat(eddardAndRobbStark).hasSize(2).contains(robb, eddard);
	}

	/**
	 * Store references to other entities without embedding all data. <br />
	 * Print out the hash structure within Redis.
	 */
	@Test
	public void useReferencesToStoreDataToOtherObjects() {

		flushTestUsers();

		eddard.setChildren(Arrays.asList(jon, robb, sansa, arya, bran, rickon));

		repository.save(eddard);

		assertThat(repository.findById(eddard.getId())).hasValueSatisfying(it -> {
			assertThat(it.getChildren()).contains(jon, robb, sansa, arya, bran, rickon);
		});

		/*
		 * Deceased:
		 *
		 * - Robb was killed by Roose Bolton during the Red Wedding.
		 * - Jon was stabbed by brothers or the Night's Watch.
		 */
		repository.deleteAll(Arrays.asList(robb, jon));

		assertThat(repository.findById(eddard.getId())).hasValueSatisfying(it -> {
			assertThat(it.getChildren()).contains(sansa, arya, bran, rickon);
			assertThat(it.getChildren()).doesNotContain(robb, jon);
		});
	}

	private void flushTestUsers() {
		repository.saveAll(Arrays.asList(eddard, robb, sansa, arya, bran, rickon, jon));
	}
}
