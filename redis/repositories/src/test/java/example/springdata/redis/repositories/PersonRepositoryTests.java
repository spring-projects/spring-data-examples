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
package example.springdata.redis.repositories;

import static org.assertj.core.api.Assertions.*;

import example.springdata.redis.test.condition.EnabledOnRedisAvailable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.domain.Example;
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

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@DataRedisTest
@EnabledOnRedisAvailable
class PersonRepositoryTests {

	/** {@link Charset} for String conversion **/
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Autowired RedisOperations<Object, Object> operations;
	@Autowired PersonRepository repository;

	/*
	 * Set of test users
	 */
	private Person eddard = new Person("eddard", "stark", Gender.MALE);
	private Person robb = new Person("robb", "stark", Gender.MALE);
	private Person sansa = new Person("sansa", "stark", Gender.FEMALE);
	private Person arya = new Person("arya", "stark", Gender.FEMALE);
	private Person bran = new Person("bran", "stark", Gender.MALE);
	private Person rickon = new Person("rickon", "stark", Gender.MALE);
	private Person jon = new Person("jon", "snow", Gender.MALE);

	@BeforeEach
	@AfterEach
	void setUp() {

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
	void saveSingleEntity() {

		repository.save(eddard);

		assertThat(operations
				.execute((RedisConnection connection) -> connection.exists(("persons:" + eddard.getId()).getBytes(CHARSET))))
						.isTrue();
	}

	/**
	 * Find entity by a single {@link Indexed} property value.
	 */
	@Test
	void findBySingleProperty() {

		flushTestUsers();

		var starks = repository.findByLastname(eddard.getLastname());

		assertThat(starks).contains(eddard, robb, sansa, arya, bran, rickon).doesNotContain(jon);
	}

	/**
	 * Find entities by multiple {@link Indexed} properties using {@literal AND}.
	 */
	@Test
	void findByMultipleProperties() {

		flushTestUsers();

		var aryaStark = repository.findByFirstnameAndLastname(arya.getFirstname(), arya.getLastname());

		assertThat(aryaStark).containsOnly(arya);
	}

	/**
	 * Find entities by multiple {@link Indexed} properties using {@literal OR}.
	 */
	@Test
	void findByMultiplePropertiesUsingOr() {

		flushTestUsers();

		var aryaAndJon = repository.findByFirstnameOrLastname(arya.getFirstname(), jon.getLastname());

		assertThat(aryaAndJon).containsOnly(arya, jon);
	}

	/**
	 * Find entities by {@link Example Query by Example}.
	 */
	@Test
	void findByQueryByExample() {

		flushTestUsers();

		var example = Example.of(new Person(null, "stark", null));

		var starks = repository.findAll(example);

		assertThat(starks).contains(arya, eddard).doesNotContain(jon);
	}

	/**
	 * Find entities in range defined by {@link Pageable}.
	 */
	@Test
	void findByReturningPage() {

		flushTestUsers();

		var page1 = repository.findPersonByLastname(eddard.getLastname(), PageRequest.of(0, 5));

		assertThat(page1.getNumberOfElements()).isEqualTo(5);
		assertThat(page1.getTotalElements()).isEqualTo(6);

		var page2 = repository.findPersonByLastname(eddard.getLastname(), PageRequest.of(1, 5));

		assertThat(page2.getNumberOfElements()).isEqualTo(1);
		assertThat(page2.getTotalElements()).isEqualTo(6);
	}

	/**
	 * Find entity by a single {@link Indexed} property on an embedded entity.
	 */
	@Test
	void findByEmbeddedProperty() {

		var winterfell = new Address();
		winterfell.setCountry("the north");
		winterfell.setCity("winterfell");

		eddard.setAddress(winterfell);

		flushTestUsers();

		var eddardStark = repository.findByAddress_City(winterfell.getCity());

		assertThat(eddardStark).containsOnly(eddard);
	}

	/**
	 * Find entity by a {@link GeoIndexed} property on an embedded entity.
	 */
	@Test
	void findByGeoLocationProperty() {

		var winterfell = new Address();
		winterfell.setCountry("the north");
		winterfell.setCity("winterfell");
		winterfell.setLocation(new Point(52.9541053, -1.2401016));

		eddard.setAddress(winterfell);

		var casterlystein = new Address();
		casterlystein.setCountry("Westerland");
		casterlystein.setCity("Casterlystein");
		casterlystein.setLocation(new Point(51.5287352, -0.3817819));

		robb.setAddress(casterlystein);

		flushTestUsers();

		var innerCircle = new Circle(new Point(51.8911912, -0.4979756), new Distance(50, Metrics.KILOMETERS));
		var eddardStark = repository.findByAddress_LocationWithin(innerCircle);

		assertThat(eddardStark).containsOnly(robb);

		var biggerCircle = new Circle(new Point(51.8911912, -0.4979756), new Distance(200, Metrics.KILOMETERS));
		var eddardAndRobbStark = repository.findByAddress_LocationWithin(biggerCircle);

		assertThat(eddardAndRobbStark).hasSize(2).contains(robb, eddard);
	}

	/**
	 * Store references to other entities without embedding all data. <br />
	 * Print out the hash structure within Redis.
	 */
	@Test
	void useReferencesToStoreDataToOtherObjects() {

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
