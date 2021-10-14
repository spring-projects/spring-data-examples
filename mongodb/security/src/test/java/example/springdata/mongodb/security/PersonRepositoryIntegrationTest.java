/*
 * Copyright 2015-2021 the original author or authors.
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
package example.springdata.mongodb.security;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.util.MongoContainers;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration test for {@link PersonRepository}.
 *
 * @author Thomas Darimont
 * @author Oliver Gierke
 */
@Testcontainers
@SpringBootTest
class PersonRepositoryIntegrationTest {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired PersonRepository repository;

	private Person dave, oliver, carter, admin;

	@BeforeEach
	void setUp() {

		repository.deleteAll();

		admin = repository.save(new Person("Admin", "Boss"));
		dave = repository.save(new Person("Dave", "Matthews"));
		oliver = repository.save(new Person("Oliver August", "Matthews"));
		carter = repository.save(new Person("Carter", "Beauford"));
	}

	@Test
	void nonAdminCallingShouldReturnOnlyItSelfAsPerson() throws Exception {

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(dave, "x"));

		var persons = repository.findAllForCurrentUserById();

		assertThat(persons).hasSize(1).containsExactly(dave);
	}

	@Test
	void adminCallingShouldReturnAllUsers() throws Exception {

		var auth = new UsernamePasswordAuthenticationToken(admin, "x",
				Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
		SecurityContextHolder.getContext().setAuthentication(auth);

		var persons = repository.findAllForCurrentUserById();

		assertThat(persons).hasSize(4).contains(admin, dave, carter, oliver);
	}
}
