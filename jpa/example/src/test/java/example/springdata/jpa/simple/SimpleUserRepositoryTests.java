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
package example.springdata.jpa.simple;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.domain.Sort.Direction.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the basic usage of {@link SimpleUserRepository}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = SimpleConfiguration.class)
public class SimpleUserRepositoryTests {

	@Autowired SimpleUserRepository repository;
	User user;

	@Before
	public void setUp() {

		user = new User();
		user.setUsername("foobar");
		user.setFirstname("firstname");
		user.setLastname("lastname");
	}

	@Test
	public void findSavedUserById() {

		user = repository.save(user);

		assertThat(repository.findOne(user.getId()), is(user));
	}

	@Test
	public void findSavedUserByLastname() throws Exception {

		user = repository.save(user);

		List<User> users = repository.findByLastname("lastname");

		assertThat(users, is(notNullValue()));
		assertThat(users.contains(user), is(true));
	}

	@Test
	public void findByFirstnameOrLastname() throws Exception {

		user = repository.save(user);

		List<User> users = repository.findByFirstnameOrLastname("lastname");

		assertThat(users.contains(user), is(true));
	}

	@Test
	public void useOptionalAsReturnAndParameterType() {

		assertThat(repository.findByUsername(Optional.of("foobar")), is(Optional.empty()));

		repository.save(user);

		assertThat(repository.findByUsername(Optional.of("foobar")).isPresent(), is(true));
	}

	@Test
	public void removeByLastname() {

		// create a 2nd user with the same lastname as user
		User user2 = new User();
		user2.setLastname(user.getLastname());

		// create a 3rd user as control group
		User user3 = new User();
		user3.setLastname("no-positive-match");

		repository.save(Arrays.asList(user, user2, user3));

		assertThat(repository.removeByLastname(user.getLastname()), is(2L));
		assertThat(repository.exists(user3.getId()), is(true));
	}

	@Test
	public void useSliceToLoadContent() {

		repository.deleteAll();

		// int repository with some values that can be ordered
		int totalNumberUsers = 11;
		List<User> source = new ArrayList<User>(totalNumberUsers);

		for (int i = 1; i <= totalNumberUsers; i++) {

			User user = new User();
			user.setLastname(this.user.getLastname());
			user.setUsername(user.getLastname() + "-" + String.format("%03d", i));
			source.add(user);
		}

		repository.save(source);

		Slice<User> users = repository.findByLastnameOrderByUsernameAsc(this.user.getLastname(), new PageRequest(1, 5));

		assertThat(users, contains(source.subList(5, 10).toArray()));
	}

	@Test
	public void findFirst2ByOrderByLastnameAsc() {

		User user0 = new User();
		user0.setLastname("lastname-0");

		User user1 = new User();
		user1.setLastname("lastname-1");

		User user2 = new User();
		user2.setLastname("lastname-2");

		// we deliberatly save the items in reverse
		repository.save(Arrays.asList(user2, user1, user0));

		List<User> result = repository.findFirst2ByOrderByLastnameAsc();

		assertThat(result.size(), is(2));
		assertThat(result, hasItems(user0, user1));
	}

	@Test
	public void findTop2ByWithSort() {

		User user0 = new User();
		user0.setLastname("lastname-0");

		User user1 = new User();
		user1.setLastname("lastname-1");

		User user2 = new User();
		user2.setLastname("lastname-2");

		// we deliberately save the items in reverse
		repository.save(Arrays.asList(user2, user1, user0));

		List<User> resultAsc = repository.findTop2By(new Sort(ASC, "lastname"));

		assertThat(resultAsc.size(), is(2));
		assertThat(resultAsc, hasItems(user0, user1));

		List<User> resultDesc = repository.findTop2By(new Sort(DESC, "lastname"));

		assertThat(resultDesc.size(), is(2));
		assertThat(resultDesc, hasItems(user1, user2));
	}

	@Test
	public void findByFirstnameOrLastnameUsingSpEL() {

		User first = new User();
		first.setLastname("lastname");

		User second = new User();
		second.setFirstname("firstname");

		User third = new User();

		repository.save(Arrays.asList(first, second, third));

		User reference = new User();
		reference.setFirstname("firstname");
		reference.setLastname("lastname");

		Iterable<User> users = repository.findByFirstnameOrLastname(reference);

		assertThat(users, is(iterableWithSize(2)));
		assertThat(users, hasItems(first, second));
	}
}
