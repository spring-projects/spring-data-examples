/*
 * Copyright 2020 the original author or authors.
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

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test showing the basic usage of {@link AuditedPersonRepository}.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicConfiguration.class)
public class AuditedPersonRepositoryTests {

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost();

	@Autowired AuditedPersonRepository repository;

	@Before
	public void setUp() {
		repository.deleteAll();
	}

	/**
	 * Saving an object using the Cassandra Repository will create a persistent representation of the object in Cassandra.
	 */
	@Test
	public void insertShouldSetCreatedDate() {

		AuditedPerson person = new AuditedPerson();
		person.setId(42L);
		person.setNew(true); // Cassandra does not support auto-generation hence we need
		// to supply whether our object is a new one.
		AuditedPerson saved = repository.save(person);

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
	public void updateShouldSetLastModifiedDate() {

		AuditedPerson person = new AuditedPerson();
		person.setId(42L);
		person.setNew(true); // Cassandra does not support auto-generation hence we need
		// to supply whether our object is a new one.
		repository.save(person);

		person.setNew(false);

		AuditedPerson modified = repository.save(person);

		assertThat(modified.getCreatedBy()).isEqualTo("Some user");
		assertThat(modified.getLastModifiedBy()).isEqualTo("Some user");
		assertThat(modified.getCreatedDate()).isBetween(Instant.now().minus(Duration.ofMinutes(1)),
				Instant.now().plus(Duration.ofMinutes(1)));
		assertThat(modified.getLastModifiedDate())
				.isBetween(Instant.now().minus(Duration.ofMinutes(1)), Instant.now().plus(Duration.ofMinutes(1)))
				.isNotEqualTo(modified.getCreatedDate());
	}

}
