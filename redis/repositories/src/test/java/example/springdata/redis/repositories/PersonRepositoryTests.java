/*
 * Copyright 2016 the original author or authors.
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
package example.springdata.redis.repositories;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsCollectionContaining.*;
import static org.hamcrest.core.IsNot.*;
import static org.junit.Assert.*;

import example.springdata.redis.test.util.EmbeddedRedisServer;
import example.springdata.redis.test.util.RequiresRedisServer;

import java.nio.charset.Charset;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationConfiguration.class)
public class PersonRepositoryTests<K, V> {

	/**
	 * We need to have a Redis server instance available. <br />
	 * 1) Start/Stop an embedded instance or reuse an already running local installation <br />
	 * 2) Ignore tests if startup failed and no server running locally.
	 */
	public static @ClassRule RuleChain rules = RuleChain
			.outerRule(EmbeddedRedisServer.runningAt(6379).suppressExceptions()).around(RequiresRedisServer.onLocalhost());

	/** {@link Charset} for String conversion **/
	private static final Charset CHARSET = Charset.forName("UTF-8");

	@Autowired RedisOperations<K, V> operations;
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

		assertThat(operations.execute(
				(RedisConnection connection) -> connection.exists(new String("persons:" + eddard.getId()).getBytes(CHARSET))),
				is(true));
	}

	/**
	 * Find entity by a single {@link Indexed} property value.
	 */
	@Test
	public void findBySingleProperty() {

		flushTestUsers();

		List<Person> starks = repository.findByLastname(eddard.getLastname());

		assertThat(starks, containsInAnyOrder(eddard, robb, sansa, arya, bran, rickon));
		assertThat(starks, not(hasItem(jon)));
	}

	/**
	 * Find entities by multiple {@link Indexed} properties using {@literal AND}.
	 */
	@Test
	public void findByMultipleProperties() {

		flushTestUsers();

		List<Person> aryaStark = repository.findByFirstnameAndLastname(arya.getFirstname(), arya.getLastname());

		assertThat(aryaStark, hasItem(arya));
		assertThat(aryaStark, not(hasItems(eddard, robb, sansa, bran, rickon, jon)));
	}

	/**
	 * Find entities by multiple {@link Indexed} properties using {@literal OR}.
	 */
	@Test
	public void findByMultiplePropertiesUsingOr() {

		flushTestUsers();

		List<Person> aryaAndJon = repository.findByFirstnameOrLastname(arya.getFirstname(), jon.getLastname());

		assertThat(aryaAndJon, containsInAnyOrder(arya, jon));
		assertThat(aryaAndJon, not(hasItems(eddard, robb, sansa, bran, rickon)));
	}

	/**
	 * Find entities in range defined by {@link Pageable}.
	 */
	@Test
	public void findByReturingPage() {

		flushTestUsers();

		Page<Person> page1 = repository.findPersonByLastname(eddard.getLastname(), new PageRequest(0, 5));

		assertThat(page1.getNumberOfElements(), is(5));
		assertThat(page1.getTotalElements(), is(6L));

		Page<Person> page2 = repository.findPersonByLastname(eddard.getLastname(), new PageRequest(1, 5));

		assertThat(page2.getNumberOfElements(), is(1));
		assertThat(page2.getTotalElements(), is(6L));
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

		assertThat(eddardStark, hasItem(eddard));
		assertThat(eddardStark, not(hasItems(robb, sansa, arya, bran, rickon, jon)));
	}

	/**
	 * Store references to other entites without embedding all data. <br />
	 * Print out the hash structure within Redis.
	 */
	@Test
	public void useReferencesToStoreDataToOtherObjects() {

		flushTestUsers();

		eddard.setChildren(Arrays.asList(jon, robb, sansa, arya, bran, rickon));

		repository.save(eddard);

		Person laoded = repository.findOne(eddard.getId());
		assertThat(laoded.getChildren(), hasItems(jon, robb, sansa, arya, bran, rickon));

		/*
		 * Deceased:
		 *  
		 * - Robb was killed by Roose Bolton during the Red Wedding.
		 * - Jon was stabbed by brothers or the Night's Watch. 
		 */
		repository.delete(Arrays.asList(robb, jon));

		laoded = repository.findOne(eddard.getId());
		assertThat(laoded.getChildren(), hasItems(sansa, arya, bran, rickon));
		assertThat(laoded.getChildren(), not(hasItems(robb, jon)));
	}

	private void flushTestUsers() {
		repository.save(Arrays.asList(eddard, robb, sansa, arya, bran, rickon, jon));
	}
}
