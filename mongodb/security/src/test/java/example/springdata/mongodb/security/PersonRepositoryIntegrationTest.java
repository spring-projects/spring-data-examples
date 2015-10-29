/*
 * Copyright 2015 the original author or authors.
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
package example.springdata.mongodb.security;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test for {@link PersonRepository}.
 * 
 * @author Thomas Darimont
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
public class PersonRepositoryIntegrationTest {

	@Autowired PersonRepository repository;

	Person dave, oliver, carter, admin;

	@Before
	public void setUp() {

		repository.deleteAll();

		admin = repository.save(new Person("Admin", "Boss"));
		dave = repository.save(new Person("Dave", "Matthews"));
		oliver = repository.save(new Person("Oliver August", "Matthews"));
		carter = repository.save(new Person("Carter", "Beauford"));
	}

	@Test
	public void nonAdminCallingShouldReturnOnlyItSelfAsPerson() throws Exception {

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(dave, "x"));

		List<Person> persons = repository.findAllForCurrentUserById();

		assertThat(persons, hasSize(1));
		assertThat(persons, contains(dave));
	}

	@Test
	public void adminCallingShouldReturnAllUsers() throws Exception {

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(admin, "x",
				Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
		SecurityContextHolder.getContext().setAuthentication(auth);

		List<Person> persons = repository.findAllForCurrentUserById();

		assertThat(persons, hasSize(4));
		assertThat(persons, containsInAnyOrder(admin, dave, carter, oliver));
	}
}
