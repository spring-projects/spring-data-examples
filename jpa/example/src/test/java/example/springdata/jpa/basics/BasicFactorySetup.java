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
package example.springdata.jpa.basics;

import static org.assertj.core.api.Assertions.*;

import example.springdata.jpa.simple.SimpleUserRepository;
import example.springdata.jpa.simple.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

/**
 * Test case showing how to use the basic {@link GenericDaoFactory}
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
class BasicFactorySetup {

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa.sample.plain");

	private SimpleUserRepository userRepository;
	private EntityManager em;

	private User user;

	/**
	 * Creates a {@link SimpleUserRepository} instance.
	 *
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() {

		em = factory.createEntityManager();

		userRepository = new JpaRepositoryFactory(em).getRepository(SimpleUserRepository.class);

		em.getTransaction().begin();

		user = new User();
		user.setUsername("username");
		user.setFirstname("firstname");
		user.setLastname("lastname");

		user = userRepository.save(user);

	}

	/**
	 * Rollback transaction.
	 */
	@AfterEach
	void tearDown() {

		em.getTransaction().rollback();
	}

	/**
	 * Showing invocation of finder method.
	 */
	@Test
	void executingFinders() {

		assertThat(userRepository.findByTheUsersName("username")).isEqualTo(user);
		assertThat(userRepository.findByLastname("lastname")).first().isEqualTo(user);
		assertThat(userRepository.findByFirstname("firstname")).first().isEqualTo(user);
	}
}
