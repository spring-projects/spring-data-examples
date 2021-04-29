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
package example;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.NonUniqueResultException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link PersonRepository} showing Vavr support at repository query methods.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
@DataJpaTest
@Transactional
public class PersonRepositoryIntegrationTests {

	@Autowired PersonRepository people;

	@SpringBootApplication
	static class Configuration {}

	/**
	 * @see #231
	 */
	@Test
	void readsPersonIntoJavaslangOptionById() {

		var dave = people.save(new Person("Dave", "Matthews"));

		var result = people.findById(dave.getId());

		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get()).isEqualTo(dave);
	}

	/**
	 * @see #231
	 */
	@Test
	void readsPeopleIntoJavaslangSeq() {

		var dave = people.save(new Person("Dave", "Matthews"));
		var carter = people.save(new Person("Carter", "Beauford"));

		var result = people.findByFirstnameContaining("art");

		assertThat(result.contains(carter)).isTrue();
		assertThat(result.contains(dave)).isFalse();
	}

	/**
	 * @see #370
	 */
	@Test
	void returnsSuccessOfOption() {

		var dave = people.save(new Person("Dave", "Matthews"));
		people.save(new Person("Carter", "Beauford"));

		var result = people.findByLastnameContaining("w");

		assertThat(result.isSuccess()).isTrue();
		assertThat(result.get()).contains(dave);
	}

	/**
	 * @see #370
	 */
	@Test
	void returnsFailureOfOption() {

		people.save(new Person("Dave", "Matthews"));
		people.save(new Person("Carter", "Beauford"));

		var result = people.findByLastnameContaining("e");

		assertThat(result.isFailure()).isTrue();
		assertThat(result.getCause()).isInstanceOf(NonUniqueResultException.class);
	}
}
