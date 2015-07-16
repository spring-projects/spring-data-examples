/*
 * Copyright 2013-2014 the original author or authors.
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
package example.springdata.cassandra;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import example.springdata.cassandra.SimpleConfiguration;
import example.springdata.cassandra.SimpleUserRepository;
import example.springdata.cassandra.User;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test showing the basic usage of {@link SimpleUserRepository}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SimpleConfiguration.class)
public class SimpleUserRepositoryTests {

	@Autowired SimpleUserRepository repository;
	User user;

	@Before
	public void setUp() {

		user = new User();
		user.setId(42L);
		user.setUsername("foobar");
		user.setFirstname("firstname");
		user.setLastname("lastname");
	}

	@Test
	public void findSavedUserById() {

		user = repository.save(user);

		assertThat(repository.findOne(user.getId()), is(user));
	}
}
