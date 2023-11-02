/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.cassandra.people;

import example.springdata.cassandra.util.CassandraKeyspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration test for {@link ReactiveCassandraTemplate}.
 *
 * @author Mark Paluch
 */
@CassandraKeyspace
@SpringBootTest
class ReactiveCassandraTemplateIntegrationTest {

	@Autowired ReactiveCassandraTemplate template;

	/**
	 * Truncate table and insert some rows.
	 */
	@BeforeEach
	void setUp() {

		var truncateAndInsert = template.truncate(Person.class) //
				.thenMany(Flux.just(new Person("Walter", "White", 50), //
						new Person("Skyler", "White", 45), //
						new Person("Saul", "Goodman", 42), //
						new Person("Jesse", "Pinkman", 27))) //
				.flatMap(template::insert);

		truncateAndInsert.as(StepVerifier::create) //
				.expectNextCount(4) //
				.verifyComplete();
	}

	/**
	 * This sample performs a count, inserts data and performs a count again using reactive operator chaining. It prints
	 * the two counts ({@code 4} and {@code 6}) to the console.
	 */
	@Test
	void shouldInsertAndCountData() {

		var saveAndCount = template.count(Person.class) //
				.doOnNext(System.out::println) //
				.thenMany(Flux.just(new Person("Hank", "Schrader", 43), //
						new Person("Mike", "Ehrmantraut", 62)))
				.flatMap(template::insert) //
				.last() //
				.flatMap(v -> template.count(Person.class)) //
				.doOnNext(System.out::println);

		saveAndCount.as(StepVerifier::create) //
				.expectNext(6L) //
				.verifyComplete();
	}
}
