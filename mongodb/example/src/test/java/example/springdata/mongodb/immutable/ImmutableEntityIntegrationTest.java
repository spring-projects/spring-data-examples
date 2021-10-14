/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.mongodb.immutable;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.util.MongoContainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration test for {@link ImmutablePerson} showing features around immutable object support.
 *
 * @author Mark Paluch
 * @author Christoph Strobl
 */
@Testcontainers
@DataMongoTest
class ImmutableEntityIntegrationTest {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired MongoOperations operations;

	@BeforeEach
	void setUp() {
		operations.dropCollection(ImmutablePerson.class);
	}

	/**
	 * Test case to show that automatically generated ids are assigned to the immutable domain object and how the
	 * {@link ImmutablePerson#getRandomNumber()} gets set via {@link ApplicationConfiguration#beforeConvertCallback()}.
	 */
	@Test
	void setsRandomNumberOnSave() {

		var unsaved = new ImmutablePerson();
		assertThat(unsaved.getRandomNumber()).isZero();

		var saved = operations.save(unsaved);

		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getRandomNumber()).isNotZero();
	}

	/**
	 * Test case to show that automatically generated ids are assigned to the immutable domain object and how the
	 * {@link ImmutableRecord#getRandomNumber()} gets set via {@link ApplicationConfiguration#beforeConvertCallback()}.
	 */
	@Test
	void setsRandomNumberOnSaveRecord() {

		var unsaved = new ImmutableRecord(null, 0);
		assertThat(unsaved.randomNumber()).isZero();

		var saved = operations.save(unsaved);

		assertThat(saved.id()).isNotNull();
		assertThat(saved.randomNumber()).isNotZero();
	}

}
