/*
 * Copyright 2016-2018 the original author or authors.
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
package example;

import static org.assertj.core.api.Assertions.*;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;

import javax.persistence.NonUniqueResultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link PersonRepository} showing Vavr support at repository query methods.
 *
 * @author Oliver Gierke
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class PersonRepositoryIntegrationTests {

	@Autowired PersonRepository people;

	@SpringBootApplication
	static class Configuration {}

	/**
	 * @see #231
	 */
	@Test
	public void readsPersonIntoJavaslangOptionById() {

		Person dave = people.save(new Person("Dave", "Matthews"));

		Option<Person> result = people.findById(dave.getId());

		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get()).isEqualTo(dave);
	}

	/**
	 * @see #231
	 */
	@Test
	public void readsPeopleIntoJavaslangSeq() {

		Person dave = people.save(new Person("Dave", "Matthews"));
		Person carter = people.save(new Person("Carter", "Beauford"));

		Seq<Person> result = people.findByFirstnameContaining("art");

		assertThat(result.contains(carter)).isTrue();
		assertThat(result.contains(dave)).isFalse();
	}

	/**
	 * @see #370
	 */
	@Test
	public void returnsSuccessOfOption() {

		Person dave = people.save(new Person("Dave", "Matthews"));
		people.save(new Person("Carter", "Beauford"));

		Try<Option<Person>> result = people.findByLastnameContaining("w");

		assertThat(result.isSuccess()).isTrue();
		assertThat(result.get()).contains(dave);
	}

	/**
	 * @see #370
	 */
	@Test
	public void returnsFailureOfOption() {

		people.save(new Person("Dave", "Matthews"));
		people.save(new Person("Carter", "Beauford"));

		Try<Option<Person>> result = people.findByLastnameContaining("e");

		assertThat(result.isFailure()).isTrue();
		assertThat(result.getCause()).isInstanceOf(NonUniqueResultException.class);
	}
}
