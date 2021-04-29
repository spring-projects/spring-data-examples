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
package example.springdata.jpa.custom;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the basic usage of {@link UserRepository}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Divya Srivastava
 */

@Transactional
@SpringBootTest
// @ActiveProfiles("jdbc") // Uncomment @ActiveProfiles to enable the JDBC Implementation of the custom repository
class UserRepositoryCustomizationTests {

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

	@Test
	void saveAndFindByLastNameAndFindByUserName() {

		var user = new User();
		user.setUsername("foobar");
		user.setLastname("lastname");

		user = repository.save(user);

		var users = repository.findByLastname("lastname");

		assertThat(users).contains(user);
		assertThat(user).isEqualTo(repository.findByTheUsersName("foobar"));
	}

	/**
	 * Test invocation of custom method.
	 */
	@Test
	void testCustomMethod() {

		var user = new User();
		user.setUsername("username");

		user = repository.save(user);

		var users = repository.myCustomBatchOperation();

		assertThat(users).contains(user);
	}
}
