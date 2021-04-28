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
package example.springdata.cassandra.auditing;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test showing the basic usage of Auditing through {@link AuditedPersonRepository}.
 *
 * @author Mark Paluch
 */
@SpringBootTest(classes = BasicConfiguration.class)
@CassandraKeyspace
class AuditedPersonRepositoryTests {

	@Autowired AuditedPersonRepository repository;

	@BeforeEach
	void setUp() {
		repository.deleteAll();
	}

	/**
	 * Saving an object using the Cassandra Repository will create a persistent representation of the object in Cassandra.
	 */
	@Test
	void insertShouldSetCreatedDate() {

		var person = new AuditedPerson();
		person.setId(42L);
		person.setNew(true); // Cassandra does not support auto-generation hence we need
		// to supply whether our object is a new one.
		var saved = repository.save(person);

		assertThat(saved.getCreatedBy()).isEqualTo("Some user");
		assertThat(saved.getLastModifiedBy()).isEqualTo("Some user");
		assertThat(saved.getCreatedDate()).isBetween(Instant.now().minus(Duration.ofMinutes(1)),
				Instant.now().plus(Duration.ofMinutes(1)));
		assertThat(saved.getLastModifiedDate()).isBetween(Instant.now().minus(Duration.ofMinutes(1)),
				Instant.now().plus(Duration.ofMinutes(1)));
	}

	/**
	 * Modifying an existing object will update the last modified fields.
	 */
	@Test
	void updateShouldSetLastModifiedDate() {

		var person = new AuditedPerson();
		person.setId(42L);
		person.setNew(true); // Cassandra does not support auto-generation hence we need
		// to supply whether our object is a new one.
		repository.save(person);

		person.setNew(false);

		var modified = repository.save(person);

		assertThat(modified.getCreatedBy()).isEqualTo("Some user");
		assertThat(modified.getLastModifiedBy()).isEqualTo("Some user");
		assertThat(modified.getCreatedDate()).isBetween(Instant.now().minus(Duration.ofMinutes(1)),
				Instant.now().plus(Duration.ofMinutes(1)));
		assertThat(modified.getLastModifiedDate())
				.isBetween(Instant.now().minus(Duration.ofMinutes(1)), Instant.now().plus(Duration.ofMinutes(1)))
				.isNotEqualTo(modified.getCreatedDate());
	}

}
