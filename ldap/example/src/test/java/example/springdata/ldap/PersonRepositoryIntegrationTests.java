/*
 * Copyright 2017 the original author or authors.
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
package example.springdata.ldap;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for {@link PersonRepository}.
 *
 * @author Mark Paluch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PersonRepositoryIntegrationTests {

	@Autowired PersonRepository personRepository;

	/**
	 * Find a {@link Person} by its Id that is a full DN.
	 *
	 * @throws InvalidNameException
	 */
	@Test
	public void findOneByName() throws InvalidNameException {

		Optional<Person> person = personRepository.findById(new LdapName("uid=bob,ou=people,dc=springframework,dc=org"));

		assertThat(person).hasValueSatisfying(it -> {
			assertThat(it.getFullName()).isEqualTo("Bob Hamilton");
			assertThat(it.getLastname()).isEqualTo("Hamilton");
			assertThat(it.getUid()).isEqualTo("bob");
		});
	}

	/**
	 * Find all entries in the base path.
	 */
	@Test
	public void findAll() {

		Iterable<Person> people = personRepository.findAll();

		assertThat(people).hasSize(3).extracting("uid").contains("bob", "joe", "ben");
	}

	/**
	 * Find all {@link Person} objects starting with {@code Ham} in the field {@code lastname}.
	 */
	@Test
	public void findByLastname() {

		List<Person> people = personRepository.findByLastnameStartsWith("Ham");

		assertThat(people).hasSize(1).extracting("uid").contains("bob");
	}

	/**
	 * Add and remove a user to the LDAP repository.
	 *
	 * @throws InvalidNameException
	 */
	@Test
	public void addUser() throws InvalidNameException {

		Person walter = new Person();
		walter.setFullName("Walter White");
		walter.setUid("heisenberg");
		walter.setLastname("White");

		personRepository.save(walter);

		List<Person> people = personRepository.findByUid("heisenberg");

		assertThat(people).hasSize(1).extracting("fullName").contains("Walter White");

		personRepository.delete(people.get(0));
	}
}
