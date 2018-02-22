/*
 * Copyright 2016-2018 the original author or authors.
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

package example.springdata.mongodb.querybyexample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.domain.ExampleMatcher.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 */
@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoOperationsIntegrationTests {

	@Autowired MongoOperations operations;

	Person skyler, walter, flynn, marie, hank;

	@Before
	public void setUp() {

		operations.remove(new Query(), Person.class);

		this.skyler = new Person("Skyler", "White", 45);
		this.walter = new Person("Walter", "White", 50);
		this.flynn = new Person("Walter Jr. (Flynn)", "White", 17);
		this.marie = new Person("Marie", "Schrader", 38);
		this.hank = new Person("Hank", "Schrader", 43);

		operations.save(this.skyler);
		operations.save(this.walter);
		operations.save(this.flynn);
		operations.save(this.marie);
		operations.save(this.hank);
	}

	/**
	 * @see #153
	 */
	@Test
	public void ignoreNullProperties() {

		Query query = query(byExample(new Person(null, null, 17)));

		assertThat(operations.find(query, Person.class), hasItems(flynn));
	}

	/**
	 * @see #153
	 */
	@Test
	public void substringMatching() {

		Example<Person> example = Example.of(new Person("er", null, null), matching().//
				withStringMatcher(StringMatcher.ENDING));

		assertThat(operations.find(query(byExample(example)), Person.class), hasItems(skyler, walter));
	}

	/**
	 * @see #154
	 */
	@Test
	public void regexMatching() {

		Example<Person> example = Example.of(new Person("(Skyl|Walt)er", null, null), matching().//
				withMatcher("firstname", matcher -> matcher.regex()));

		assertThat(operations.find(query(byExample(example)), Person.class), hasItems(skyler, walter));
	}

	/**
	 * @see #153
	 */
	@Test
	public void matchStartingStringsIgnoreCase() {

		Example<Person> example = Example.of(new Person("Walter", "WHITE", null), matching(). //
				withIgnorePaths("age").//
				withMatcher("firstname", startsWith()).//
				withMatcher("lastname", ignoreCase()));

		assertThat(operations.find(query(byExample(example)), Person.class), hasItems(flynn, walter));
	}

	/**
	 * @see #153
	 */
	@Test
	public void configuringMatchersUsingLambdas() {

		Example<Person> example = Example.of(new Person("Walter", "WHITE", null), matching().//
				withIgnorePaths("age"). //
				withMatcher("firstname", matcher -> matcher.startsWith()). //
				withMatcher("lastname", matcher -> matcher.ignoreCase()));

		assertThat(operations.find(query(byExample(example)), Person.class), hasItems(flynn, walter));
	}

	/**
	 * @see #153
	 */
	@Test
	public void valueTransformer() {

		Example<Person> example = Example.of(new Person(null, "White", 99), matching(). //
				withMatcher("age", matcher -> matcher.transform(value -> Optional.of(Integer.valueOf(50)))));

		assertThat(operations.find(query(byExample(example)), Person.class), hasItems(walter));
	}
}
