/*
 * Copyright 2018-2021 the original author or authors.
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
package example.springdata.cassandra.events;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Query;

/**
 * Test showing differences between fetching results as {@link List} and {@link Stream streaming} results using
 * Cassandra Lifecyle Events.
 *
 * @author Mark Paluch
 */
@SpringBootTest(classes = BasicConfiguration.class)
@CassandraKeyspace
class LifecycleEventsTests {

	@Autowired CassandraOperations operations;

	@Test
	void shouldStreamEntities() {

		insertEntities();

		var userStream = operations.stream(Query.empty(), User.class);
		userStream.forEach(System.out::println);
	}

	@Test
	void shouldReturnEntitiesAsList() {

		insertEntities();

		var userStream = operations.select(Query.empty(), User.class);
		userStream.forEach(System.out::println);
	}

	private void insertEntities() {

		var walter = new User(1, "Walter", "White");
		var skyler = new User(2, "Skyler", "White");
		var jesse = new User(3, "Jesse Pinkman", "Jesse Pinkman");

		operations.truncate(User.class);

		operations.insert(walter);
		operations.insert(skyler);
		operations.insert(jesse);
	}
}
