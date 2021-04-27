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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.EntityWriteResult;
import org.springframework.data.cassandra.core.UpdateOptions;
import org.springframework.data.cassandra.core.query.Criteria;

/**
 * Integration test showing the basic usage of Optimistic Locking through {@link OptimisticPersonRepository}.
 *
 * @author Mark Paluch
 */
@SpringBootTest(classes = BasicConfiguration.class)
public class OptimisticPersonRepositoryTests {

	@Autowired OptimisticPersonRepository repository;
	@Autowired CassandraOperations operations;

	@BeforeEach
	public void setUp() {
		repository.deleteAll();
	}

	/**
	 * Saving an object using the Cassandra Repository will create a persistent representation of the object in Cassandra
	 * and increment the version property.
	 */
	@Test
	public void insertShouldIncrementVersion() {

		OptimisticPerson person = new OptimisticPerson();
		person.setId(42L);
		person.setName("Walter White");

		OptimisticPerson saved = repository.save(person);

		assertThat(saved.getVersion()).isGreaterThan(0);
	}

	/**
	 * Modifying an existing object will update the last modified fields.
	 */
	@Test
	public void updateShouldDetectChangedEntity() {

		OptimisticPerson person = new OptimisticPerson();
		person.setId(42L);
		person.setName("Walter White");

		// Load the person because we intend to change it.
		OptimisticPerson saved = repository.save(person);

		// Another process has changed the person object in the meantime.
		OptimisticPerson anotherProcess = repository.findById(person.getId()).get();
		anotherProcess.setName("Heisenberg");
		repository.save(anotherProcess);

		// Now it's our turn to modify the object...
		saved.setName("Walter");

		// ...which fails with a OptimisticLockingFailureException, using LWT under the hood.
		assertThatExceptionOfType(OptimisticLockingFailureException.class).isThrownBy(() -> repository.save(saved));
	}

	/**
	 * This tests uses lightweight transactions by leveraging mapped {@code IF} conditions with the {@code UPDATE}
	 * statement through {@link CassandraOperations#update(Object, UpdateOptions)}.
	 */
	@Test
	public void updateUsingLightWeightTransactions() {

		SimplePerson person = new SimplePerson();
		person.setId(42L);
		person.setName("Walter White");

		operations.insert(person);

		EntityWriteResult<SimplePerson> success = operations.update(person,
				UpdateOptions.builder().ifCondition(Criteria.where("name").is("Walter White")).build());

		assertThat(success.wasApplied()).isTrue();

		EntityWriteResult<SimplePerson> failed = operations.update(person,
				UpdateOptions.builder().ifCondition(Criteria.where("name").is("Heisenberg")).build());

		assertThat(failed.wasApplied()).isFalse();
	}

}
