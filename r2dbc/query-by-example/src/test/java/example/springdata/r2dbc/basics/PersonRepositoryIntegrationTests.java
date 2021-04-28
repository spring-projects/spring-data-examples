/*
 * Copyright 2021-2021 the original author or authors.
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
package example.springdata.r2dbc.basics;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.r2dbc.core.DatabaseClient;

/**
 * Integration test showing the usage of R2DBC Query-by-Example support through Spring Data repositories.
 *
 * @author Mark Paluch
 */
@SpringBootTest(classes = InfrastructureConfiguration.class)
class PersonRepositoryIntegrationTests {

	@Autowired PersonRepository people;
	@Autowired DatabaseClient client;

	private Person skyler, walter, flynn, marie, hank;

	@BeforeEach
	void setUp() {

		Hooks.onOperatorDebug();

		var statements = Arrays.asList(//
				"DROP TABLE IF EXISTS person;",
				"CREATE TABLE person (id SERIAL PRIMARY KEY, firstname VARCHAR(100) NOT NULL, lastname VARCHAR(100) NOT NULL, age INTEGER NOT NULL);");

		this.skyler = new Person(null, "Skyler", "White", 45);
		this.walter = new Person(null, "Walter", "White", 50);
		this.flynn = new Person(null, "Walter Jr. (Flynn)", "White", 17);
		this.marie = new Person(null, "Marie", "Schrader", 38);
		this.hank = new Person(null, "Hank", "Schrader", 43);

		statements.stream().map(client::sql) //
				.map(DatabaseClient.GenericExecuteSpec::then) //
				.forEach(it -> it.as(StepVerifier::create).verifyComplete());

		people.saveAll(Arrays.asList(skyler, walter, flynn, marie, hank)) //
				.as(StepVerifier::create) //
				.expectNextCount(5) //
				.verifyComplete();
	}

	@Test
	void countBySimpleExample() {

		var example = Example.of(new Person(null, null, "White", null));

		people.count(example).as(StepVerifier::create) //
				.expectNext(3L) //
				.verifyComplete();
	}

	@Test
	void ignorePropertiesAndMatchByAge() {

		var example = Example.of(flynn, matching(). //
				withIgnorePaths("firstname", "lastname"));

		people.findOne(example) //
				.as(StepVerifier::create) //
				.expectNext(flynn) //
				.verifyComplete();
	}

	@Test
	void substringMatching() {

		var example = Example.of(new Person(null, "er", null, null), matching(). //
				withStringMatcher(StringMatcher.ENDING));

		people.findAll(example).collectList() //
				.as(StepVerifier::create) //
				.consumeNextWith(actual -> {
					assertThat(actual).containsExactlyInAnyOrder(skyler, walter);
				}) //
				.verifyComplete();
	}

	@Test
	void matchStartingStringsIgnoreCase() {

		var example = Example.of(new Person(null, "Walter", "WHITE", null), matching(). //
				withIgnorePaths("age"). //
				withMatcher("firstname", startsWith()). //
				withMatcher("lastname", ignoreCase()));

		people.findAll(example).collectList() //
				.as(StepVerifier::create) //
				.consumeNextWith(actual -> {
					assertThat(actual).containsExactlyInAnyOrder(flynn, walter);
				}) //
				.verifyComplete();
	}

	@Test
	void configuringMatchersUsingLambdas() {

		var example = Example.of(new Person(null, "Walter", "WHITE", null), matching(). //
				withIgnorePaths("age"). //
				withMatcher("firstname", GenericPropertyMatcher::startsWith). //
				withMatcher("lastname", GenericPropertyMatcher::ignoreCase));

		people.findAll(example).collectList() //
				.as(StepVerifier::create) //
				.consumeNextWith(actual -> {
					assertThat(actual).containsExactlyInAnyOrder(flynn, walter);
				}) //
				.verifyComplete();
	}

	@Test
	void valueTransformer() {

		var example = Example.of(new Person(null, null, "White", 99), matching(). //
				withMatcher("age", matcher -> matcher.transform(value -> Optional.of(50))));

		people.findAll(example).collectList() //
				.as(StepVerifier::create) //
				.consumeNextWith(actual -> {
					assertThat(actual).containsExactlyInAnyOrder(walter);
				}) //
				.verifyComplete();
	}
}
