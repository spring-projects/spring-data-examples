/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.jpa.compositions;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the usage of a composite repository via {@link UserRepository}.
 *
 * @author Mark Paluch
 * @author Divya Srivastava
 */

@Transactional
@SpringBootTest
class ComposedRepositoryTests {

	@Autowired UserRepository repository;

	/**
	 * Tests inserting a user and asserts it can be loaded again.
	 */
	@Test
	void testInsert() {

		var user = new User();
		user.setUsername("username");

		user = repository.save(user);

		assertThat(repository.findById(user.getId())).hasValue(user);
	}

	/**
	 * Testing {@link ContactRepository} fragment.
	 */
	@Test
	void testContactRepository() {

		var walter = new User();
		walter.setUsername("heisenberg");
		walter.setFirstname("Walter");
		walter.setLastname("White");

		var walterJr = new User();
		walterJr.setUsername("flynn");
		walterJr.setFirstname("Walter Jr.");
		walterJr.setLastname("White");

		repository.saveAll(Arrays.asList(walter, walterJr));

		assertThat(repository.findRelatives(walter)).contains(walterJr);
	}

	/**
	 * Testing {@link EmployeeRepository} fragment.
	 */
	@Test
	void testFindCoworkers() {

		var gustavo = new User();
		gustavo.setUsername("pollosh");
		gustavo.setFirstname("Gustavo");
		gustavo.setLastname("Fring");

		var walter = new User();
		walter.setUsername("heisenberg");
		walter.setFirstname("Walter");
		walter.setLastname("White");
		walter.setManager(gustavo);

		var jesse = new User();
		jesse.setUsername("capncook");
		jesse.setFirstname("Jesse");
		jesse.setLastname("Pinkman");
		jesse.setManager(gustavo);

		repository.saveAll(Arrays.asList(gustavo, walter, jesse));

		assertThat(repository.findCoworkers(walter)).contains(jesse);
	}

	/**
	 * Testing {@link EmployeeRepository} fragment.
	 */
	@Test
	void testFindSubordinates() {

		var gustavo = new User();
		gustavo.setUsername("pollosh");
		gustavo.setFirstname("Gustavo");
		gustavo.setLastname("Fring");

		var walter = new User();
		walter.setUsername("heisenberg");
		walter.setFirstname("Walter");
		walter.setLastname("White");
		walter.setManager(gustavo);

		var jesse = new User();
		jesse.setUsername("capncook");
		jesse.setFirstname("Jesse");
		jesse.setLastname("Pinkman");
		jesse.setManager(gustavo);

		repository.saveAll(Arrays.asList(gustavo, walter, jesse));

		assertThat(repository.findSubordinates(gustavo)).contains(walter, jesse);
		assertThat(repository.findSubordinates(walter)).isEmpty();
	}
}
