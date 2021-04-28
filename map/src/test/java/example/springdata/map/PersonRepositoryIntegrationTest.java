/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.map;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.map.repository.config.EnableMapRepositories;

/**
 * Integration tests for {@link PersonRepository}.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
@SpringBootTest
class PersonRepositoryIntegrationTest {

	@SpringBootApplication
	@EnableMapRepositories
	static class Config {}

	@Autowired PersonRepository repository;

	@Test
	void storesPerson() {

		var person = repository.save(new Person("Dave", "Matthews", 47));

		assertThat(repository.findById(person.getId())).hasValue(person);
	}

	@Test
	void findsPersonByAge() {

		var dave = repository.save(new Person("Dave", "Matthews", 47));
		var oliver = repository.save(new Person("Oliver August", "Matthews", 7));

		var result = repository.findByAgeGreaterThan(18);

		assertThat(result).contains(dave).doesNotContain(oliver);
	}
}
