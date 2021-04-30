/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.cassandra.optimisticlocking;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.UpdateOptions;
import org.springframework.data.cassandra.core.query.Criteria;

/**
 * Integration test showing the basic usage of Optimistic Locking through {@link OptimisticPersonRepository}.
 *
 * @author Mark Paluch
 */
@SpringBootTest(classes = BasicConfiguration.class)
@CassandraKeyspace
class OptimisticPersonRepositoryTests {

	@Autowired OptimisticPersonRepository repository;
	@Autowired CassandraOperations operations;

	@BeforeEach
	void setUp() {
		repository.deleteAll();
	}

	/**
	 * Saving an object using the Cassandra Repository will create a persistent representation of the object in Cassandra
	 * and increment the version property.
	 */
	@Test
	void insertShouldIncrementVersion() {

		var person = new OptimisticPerson(42L, 0, "Walter White");

		var saved = repository.save(person);

		assertThat(saved.version()).isGreaterThan(0);
	}

	/**
	 * Modifying an existing object will update the last modified fields.
	 */
	@Test
	void updateShouldDetectChangedEntity() {

		var person = new OptimisticPerson(42L, 0, "Walter White");

		// Load the person because we intend to change it.
		var saved = repository.save(person);

		// Another process has changed the person object in the meantime.
		var anotherProcess = repository.findById(person.id()).get();
		anotherProcess = anotherProcess.withName("Heisenberg");
		repository.save(anotherProcess);

		// Now it's our turn to modify the object...
		var ourSaved = saved.withName("Walter");

		// ...which fails with a OptimisticLockingFailureException, using LWT under the hood.
		assertThatExceptionOfType(OptimisticLockingFailureException.class).isThrownBy(() -> repository.save(ourSaved));
	}

	/**
	 * This tests uses lightweight transactions by leveraging mapped {@code IF} conditions with the {@code UPDATE}
	 * statement through {@link CassandraOperations#update(Object, UpdateOptions)}.
	 */
	@Test
	void updateUsingLightWeightTransactions() {

		var person = new SimplePerson();
		person.setId(42L);
		person.setName("Walter White");

		operations.insert(person);

		var success = operations.update(person,
				UpdateOptions.builder().ifCondition(Criteria.where("name").is("Walter White")).build());

		assertThat(success.wasApplied()).isTrue();

		var failed = operations.update(person,
				UpdateOptions.builder().ifCondition(Criteria.where("name").is("Heisenberg")).build());

		assertThat(failed.wasApplied()).isFalse();
	}

}
