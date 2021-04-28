/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.cassandra.streamoptional;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test to show the usage of Java {@link Stream} and {@link Optional} features with Spring Data Cassandra.
 *
 * @author Mark Paluch
 */
@CassandraKeyspace
@SpringBootTest(classes = CassandraConfiguration.class)
class StreamOptionalIntegrationTests {

	@Autowired PersonRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		repository.deleteAll();
	}

	@Test
	void providesFindOneWithOptional() {

		var homer = repository.save(new Person("1", "Homer", "Simpson"));

		assertThat(repository.findById(homer.id).isPresent()).isTrue();
		assertThat(repository.findById(homer.id + 1)).isEqualTo(Optional.<Person> empty());
	}

	@Test
	void invokesDefaultMethod() {

		var homer = repository.save(new Person("1", "Homer", "Simpson"));
		var result = repository.findByPerson(homer);

		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(homer);
	}

	/**
	 * Streaming data from the store by using a repository method that returns a {@link Stream}. Note, that since the
	 * resulting {@link Stream} contains state it needs to be closed explicitly after use!
	 */
	@Test
	void useJava8StreamsWithCustomQuery() {

		var homer = repository.save(new Person("1", "Homer", "Simpson"));
		var bart = repository.save(new Person("2", "Bart", "Simpson"));

		try (var stream = repository.findAll()) {
			assertThat(stream.collect(Collectors.toList())).contains(homer, bart);
		}
	}
}
