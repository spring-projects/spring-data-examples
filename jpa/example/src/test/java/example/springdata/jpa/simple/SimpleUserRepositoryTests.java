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
package example.springdata.jpa.simple;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.*;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the basic usage of {@link SimpleUserRepository}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 * @author Divya Srivastava
 * @author Jens Schauder
 */
@Transactional
@SpringBootTest
@Slf4j
class SimpleUserRepositoryTests {

	@Autowired SimpleUserRepository repository;
	private User user;

	@BeforeEach
	void setUp() {

		user = new User();
		user.setUsername("foobar");
		user.setFirstname("firstname");
		user.setLastname("lastname");
	}

	@Test
	void findSavedUserById() {

		user = repository.save(user);

		assertThat(repository.findById(user.getId())).hasValue(user);
	}

	@Test
	void findSavedUserByLastname() {

		user = repository.save(user);

		assertThat(repository.findByLastname("lastname")).contains(user);
	}

	@Test
	void findByFirstnameOrLastname() {

		user = repository.save(user);

		assertThat(repository.findByFirstnameOrLastname("lastname")).contains(user);
	}

	@Test
	void useOptionalAsReturnAndParameterType() {

		assertThat(repository.findByUsername(Optional.of("foobar"))).isEmpty();

		repository.save(user);

		assertThat(repository.findByUsername(Optional.of("foobar"))).isPresent();
	}

	@Test
	void removeByLastname() {

		// create a 2nd user with the same lastname as user
		var user2 = new User();
		user2.setLastname(user.getLastname());

		// create a 3rd user as control group
		var user3 = new User();
		user3.setLastname("no-positive-match");

		repository.saveAll(Arrays.asList(user, user2, user3));

		assertThat(repository.removeByLastname(user.getLastname())).isEqualTo(2L);
		assertThat(repository.existsById(user3.getId())).isTrue();
	}

	@Test
	void useSliceToLoadContent() {

		repository.deleteAll();

		// int repository with some values that can be ordered
		var totalNumberUsers = 11;
		List<User> source = new ArrayList<>(totalNumberUsers);

		for (var i = 1; i <= totalNumberUsers; i++) {

			var user = new User();
			user.setLastname(this.user.getLastname());
			user.setUsername(user.getLastname() + "-" + String.format("%03d", i));
			source.add(user);
		}

		repository.saveAll(source);

		var users = repository.findByLastnameOrderByUsernameAsc(this.user.getLastname(), PageRequest.of(1, 5));

		assertThat(users).containsAll(source.subList(5, 10));
	}

	@Test
	void findFirst2ByOrderByLastnameAsc() {

		var user0 = new User();
		user0.setLastname("lastname-0");

		var user1 = new User();
		user1.setLastname("lastname-1");

		var user2 = new User();
		user2.setLastname("lastname-2");

		// we deliberatly save the items in reverse
		repository.saveAll(Arrays.asList(user2, user1, user0));

		var result = repository.findFirst2ByOrderByLastnameAsc();

		assertThat(result).containsExactly(user0, user1);
	}

	@Test
	void findTop2ByWithSort() {

		var user0 = new User();
		user0.setLastname("lastname-0");

		var user1 = new User();
		user1.setLastname("lastname-1");

		var user2 = new User();
		user2.setLastname("lastname-2");

		// we deliberately save the items in reverse
		repository.saveAll(Arrays.asList(user2, user1, user0));

		var resultAsc = repository.findTop2By(Sort.by(ASC, "lastname"));

		assertThat(resultAsc).containsExactly(user0, user1);

		var resultDesc = repository.findTop2By(Sort.by(DESC, "lastname"));

		assertThat(resultDesc).containsExactly(user2, user1);
	}

	@Test
	void findByFirstnameOrLastnameUsingSpEL() {

		var first = new User();
		first.setLastname("lastname");

		var second = new User();
		second.setFirstname("firstname");

		var third = new User();

		repository.saveAll(Arrays.asList(first, second, third));

		var reference = new User();
		reference.setFirstname("firstname");
		reference.setLastname("lastname");

		var users = repository.findByFirstnameOrLastname(reference);

		assertThat(users).containsExactly(first, second);
	}

	/**
	 * Streaming data from the store by using a repository method that returns a {@link Stream}. Note, that since the
	 * resulting {@link Stream} contains state it needs to be closed explicitly after use!
	 */
	@Test
	void useJava8StreamsWithCustomQuery() {

		var user1 = repository.save(new User("Customer1", "Foo"));
		var user2 = repository.save(new User("Customer2", "Bar"));

		try (var stream = repository.streamAllCustomers()) {
			assertThat(stream.collect(Collectors.toList())).contains(user1, user2);
		}
	}

	/**
	 * Streaming data from the store by using a repository method that returns a {@link Stream} with a derived query.
	 * Note, that since the resulting {@link Stream} contains state it needs to be closed explicitly after use!
	 */
	@Test
	void useJava8StreamsWithDerivedQuery() {

		var user1 = repository.save(new User("Customer1", "Foo"));
		var user2 = repository.save(new User("Customer2", "Bar"));

		try (var stream = repository.findAllByLastnameIsNotNull()) {
			assertThat(stream.collect(Collectors.toList())).contains(user1, user2);
		}
	}

	/**
	 * Query methods using streaming need to be used inside a surrounding transaction to keep the connection open while
	 * the stream is consumed. We simulate that not being the case by actively disabling the transaction here.
	 */
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void rejectsStreamExecutionIfNoSurroundingTransactionActive() {
		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			repository.findAllByLastnameIsNotNull();
		});
	}

	/**
	 * Here we demonstrate the usage of {@link CompletableFuture} as a result wrapper for asynchronous repository query
	 * methods. Note, that we need to disable the surrounding transaction to be able to asynchronously read the written
	 * data from from another thread within the same test method.
	 */
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void supportsCompletableFuturesAsReturnTypeWrapper() throws Exception {

		repository.save(new User("Customer1", "Foo"));
		repository.save(new User("Customer2", "Bar"));

		var future = repository.readAllBy().thenAccept(users -> {

			assertThat(users).hasSize(2);
			users.forEach(customer -> log.info(customer.toString()));
			log.info("Completed!");
		});

		while (!future.isDone()) {
			log.info("Waiting for the CompletableFuture to finish...");
			TimeUnit.MILLISECONDS.sleep(500);
		}

		future.get();

		log.info("Done!");

		repository.deleteAll();
	}
}
