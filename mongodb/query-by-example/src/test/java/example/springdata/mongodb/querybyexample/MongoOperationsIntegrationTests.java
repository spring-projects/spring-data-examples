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

package example.springdata.mongodb.querybyexample;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import example.springdata.mongodb.util.MongoContainers;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 */
@SuppressWarnings("unused")
@Testcontainers
@DataMongoTest
class MongoOperationsIntegrationTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired MongoOperations operations;

	private Person skyler, walter, flynn, marie, hank;

	@BeforeEach
	void setUp() {

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
	void ignoreNullProperties() {

		var query = query(byExample(new Person(null, null, 17)));

		assertThat(operations.find(query, Person.class)).contains(flynn);
	}

	/**
	 * @see #153
	 */
	@Test
	void substringMatching() {

		var example = Example.of(new Person("er", null, null), matching().//
				withStringMatcher(StringMatcher.ENDING));

		assertThat(operations.find(query(byExample(example)), Person.class)).contains(skyler, walter);
	}

	/**
	 * @see #154
	 */
	@Test
	void regexMatching() {

		var example = Example.of(new Person("(Skyl|Walt)er", null, null), matching().//
				withMatcher("firstname", GenericPropertyMatcher::regex));

		assertThat(operations.find(query(byExample(example)), Person.class)).contains(skyler, walter);
	}

	/**
	 * @see #153
	 */
	@Test
	void matchStartingStringsIgnoreCase() {

		var example = Example.of(new Person("Walter", "WHITE", null), matching(). //
				withIgnorePaths("age").//
				withMatcher("firstname", startsWith()).//
				withMatcher("lastname", ignoreCase()));

		assertThat(operations.find(query(byExample(example)), Person.class)).contains(flynn, walter);
	}

	/**
	 * @see #153
	 */
	@Test
	void configuringMatchersUsingLambdas() {

		var example = Example.of(new Person("Walter", "WHITE", null), matching().//
				withIgnorePaths("age"). //
				withMatcher("firstname", GenericPropertyMatcher::startsWith). //
				withMatcher("lastname", GenericPropertyMatcher::ignoreCase));

		assertThat(operations.find(query(byExample(example)), Person.class)).contains(flynn, walter);
	}

	/**
	 * @see #153
	 */
	@Test
	void valueTransformer() {

		var example = Example.of(new Person(null, "White", 99), matching(). //
				withMatcher("age", matcher -> matcher.transform(value -> Optional.of(50))));

		assertThat(operations.find(query(byExample(example)), Person.class)).contains(walter);
	}
}
