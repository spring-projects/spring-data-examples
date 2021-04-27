/*
 * Copyright 2013-2021 the original author or authors.
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
package example.springdata.cassandra.basic;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assumptions.*;

import example.springdata.cassandra.util.CassandraKeyspace;
import example.springdata.cassandra.util.CassandraVersion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Version;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * Integration test showing the basic usage of {@link BasicUserRepository}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 * @author Mark Paluch
 */
@SpringBootTest(classes = BasicConfiguration.class)
@CassandraKeyspace
class BasicUserRepositoryTests {

	private final static Version CASSANDRA_3_4 = Version.parse("3.4");

	@Autowired BasicUserRepository repository;
	@Autowired CqlSession session;
	private User user;

	@BeforeEach
	void setUp() {

		user = new User();
		user.setId(42L);
		user.setUsername("foobar");
		user.setFirstname("firstname");
		user.setLastname("lastname");
	}

	/**
	 * Saving an object using the Cassandra Repository will create a persistent representation of the object in Cassandra.
	 */
	@Test
	void findSavedUserById() {

		user = repository.save(user);

		assertThat(repository.findById(user.getId())).contains(user);
	}

	/**
	 * Cassandra can be queries by using query methods annotated with {@link @Query}.
	 */
	@Test
	void findByAnnotatedQueryMethod() {

		repository.save(user);

		assertThat(repository.findUserByIdIn(1000)).isNull();
		assertThat(repository.findUserByIdIn(42)).isEqualTo(user);
	}

	/**
	 * Spring Data Cassandra supports query derivation so annotating query methods with
	 * {@link org.springframework.data.cassandra.repository.Query} is optional. Querying columns other than the primary
	 * key requires a secondary index.
	 */
	@Test
	void findByDerivedQueryMethod() throws InterruptedException {

		session.execute("CREATE INDEX IF NOT EXISTS user_username ON users (uname);");
		/*
		  Cassandra secondary indexes are created in the background without the possibility to check
		  whether they are available or not. So we are forced to just wait. *sigh*
		 */
		Thread.sleep(1000);

		repository.save(user);

		assertThat(repository.findUserByUsername(user.getUsername())).isEqualTo(user);
	}

	/**
	 * Spring Data Cassandra supports {@code LIKE} and {@code CONTAINS} query keywords to for SASI indexes.
	 */
	@Test
	void findByDerivedQueryMethodWithSASI() throws InterruptedException {

		assumeThat(CassandraVersion.getReleaseVersion(session).isGreaterThanOrEqualTo(CASSANDRA_3_4)).isTrue();

		session.execute("CREATE CUSTOM INDEX ON users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex';");
		/*
		  Cassandra secondary indexes are created in the background without the possibility to check
		  whether they are available or not. So we are forced to just wait. *sigh*
		 */
		Thread.sleep(1000);

		repository.save(user);

		assertThat(repository.findUsersByLastnameStartsWith("last")).contains(user);
	}
}
