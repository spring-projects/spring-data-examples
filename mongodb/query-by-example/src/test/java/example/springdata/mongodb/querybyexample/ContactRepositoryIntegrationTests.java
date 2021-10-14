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

import example.springdata.mongodb.util.MongoContainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories for a case
 * where two domain types are stored in one collection.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @soundtrack Paul van Dyk - VONYC Sessions Episode 496 with guest Armin van Buuren
 */
@Testcontainers
@DataMongoTest
class ContactRepositoryIntegrationTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired UserRepository userRepository;
	@Autowired ContactRepository contactRepository;
	@Autowired MongoOperations mongoOperations;

	private Person skyler, walter, flynn;
	private Relative marie, hank;

	@BeforeEach
	void setUp() {

		contactRepository.deleteAll();

		this.skyler = contactRepository.save(new Person("Skyler", "White", 45));
		this.walter = contactRepository.save(new Person("Walter", "White", 50));
		this.flynn = contactRepository.save(new Person("Walter Jr. (Flynn)", "White", 17));
		this.marie = contactRepository.save(new Relative("Marie", "Schrader", 38));
		this.hank = contactRepository.save(new Relative("Hank", "Schrader", 43));
	}

	/**
	 * @see #153
	 */
	@Test
	void countByConcreteSubtypeExample() {

		var example = Example.of(new Person(null, null, null));

		assertThat(userRepository.count(example)).isEqualTo(3L);
	}

	/**
	 * @see #153
	 */
	@Test
	void findAllPersonsBySimpleExample() {

		var example = Example.of(new Person(".*", null, null), //
				matching().withStringMatcher(StringMatcher.REGEX));

		assertThat(userRepository.findAll(example)).contains(skyler, walter, flynn);
		assertThat((Iterable) userRepository.findAll(example)).doesNotContain(hank, marie);
	}

	/**
	 * @see #153
	 */
	@Test
	void findAllRelativesBySimpleExample() {

		var example = Example.of(new Relative(".*", null, null), //
				matching().withStringMatcher(StringMatcher.REGEX));

		assertThat(contactRepository.findAll(example)).contains(hank, marie);
		assertThat((Iterable) contactRepository.findAll(example)).doesNotContain(skyler, walter, flynn);
	}
}
