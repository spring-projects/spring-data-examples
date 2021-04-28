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

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.*;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @author Jens Schauder
 */
@SuppressWarnings("unused")
@DataMongoTest
class UserRepositoryIntegrationTests {

	@Autowired UserRepository repository;

	private Person skyler, walter, flynn, marie, hank;

	@BeforeEach
	void setUp() {

		repository.deleteAll();

		this.skyler = repository.save(new Person("Skyler", "White", 45));
		this.walter = repository.save(new Person("Walter", "White", 50));
		this.flynn = repository.save(new Person("Walter Jr. (Flynn)", "White", 17));
		this.marie = repository.save(new Person("Marie", "Schrader", 38));
		this.hank = repository.save(new Person("Hank", "Schrader", 43));
	}

	/**
	 * @see #153
	 */
	@Test
	void countBySimpleExample() {

		var example = Example.of(new Person(null, "White", null));

		assertThat(repository.count(example)).isEqualTo(3L);
	}

	/**
	 * @see #153
	 */
	@Test
	void ignorePropertiesAndMatchByAge() {

		var example = Example.of(flynn, matching(). //
				withIgnorePaths("firstname", "lastname"));

		assertThat(repository.findOne(example)).contains(flynn);
	}

	/**
	 * @see #153
	 */
	@Test
	void substringMatching() {

		var example = Example.of(new Person("er", null, null), matching(). //
				withStringMatcher(StringMatcher.ENDING));

		assertThat(repository.findAll(example)).containsExactlyInAnyOrder(skyler, walter);
	}

	/**
	 * @see #153
	 */
	@Test
	void regexMatching() {

		var example = Example.of(new Person("(Skyl|Walt)er", null, null), matching(). //
				withMatcher("firstname", GenericPropertyMatcher::regex));

		assertThat(repository.findAll(example)).contains(skyler, walter);
	}

	/**
	 * @see #153
	 */
	@Test
	void matchStartingStringsIgnoreCase() {

		var example = Example.of(new Person("Walter", "WHITE", null), matching(). //
				withIgnorePaths("age"). //
				withMatcher("firstname", startsWith()). //
				withMatcher("lastname", ignoreCase()));

		assertThat(repository.findAll(example)).containsExactlyInAnyOrder(flynn, walter);
	}

	/**
	 * @see #153
	 */
	@Test
	void configuringMatchersUsingLambdas() {

		var example = Example.of(new Person("Walter", "WHITE", null), matching(). //
				withIgnorePaths("age"). //
				withMatcher("firstname", GenericPropertyMatcher::startsWith). //
				withMatcher("lastname", GenericPropertyMatcher::ignoreCase));

		assertThat(repository.findAll(example)).containsExactlyInAnyOrder(flynn, walter);
	}

	/**
	 * @see #153
	 */
	@Test
	void valueTransformer() {

		var example = Example.of(new Person(null, "White", 99), matching(). //
				withMatcher("age", matcher -> matcher.transform(value -> Optional.of(50))));

		assertThat(repository.findAll(example)).containsExactlyInAnyOrder(walter);
	}
}
