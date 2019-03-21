/*
 * Copyright 2016-2018 the original author or authors.
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
package example.springdata.cassandra.java8;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Version;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test to show the usage of Java 8 features with Spring Data Cassandra.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CassandraConfiguration.class)
public class Java8IntegrationTests {

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost()
			.atLeast(Version.parse("3.0"));

	@Autowired PersonRepository repository;

	@Before
	public void setUp() throws Exception {
		repository.deleteAll();
	}

	@Test
	public void providesFindOneWithOptional() {

		Person homer = repository.save(new Person("1", "Homer", "Simpson"));

		assertThat(repository.findById(homer.id).isPresent()).isTrue();
		assertThat(repository.findById(homer.id + 1)).isEqualTo(Optional.<Person> empty());
	}

	@Test
	public void invokesDefaultMethod() {

		Person homer = repository.save(new Person("1", "Homer", "Simpson"));
		Optional<Person> result = repository.findByPerson(homer);

		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(homer);
	}

	/**
	 * Streaming data from the store by using a repository method that returns a {@link Stream}. Note, that since the
	 * resulting {@link Stream} contains state it needs to be closed explicitly after use!
	 */
	@Test
	public void useJava8StreamsWithCustomQuery() {

		Person homer = repository.save(new Person("1", "Homer", "Simpson"));
		Person bart = repository.save(new Person("2", "Bart", "Simpson"));

		try (Stream<Person> stream = repository.findAll()) {
			assertThat(stream.collect(Collectors.toList())).contains(homer, bart);
		}
	}
}
