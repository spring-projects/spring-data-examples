/*
 * Copyright 2018 the original author or authors.
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
package example;

import static org.assertj.core.api.Assertions.*;

import example.mongo.Person;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests for {@link PersonController}.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerTests {

	@Autowired ApplicationContext context;

	WebTestClient rest;

	@Before
	public void setup() {
		this.rest = WebTestClient.bindToApplicationContext(this.context).configureClient().build();
	}

	/**
	 * Read a collection of {@link Person}.
	 */
	@Test
	public void shouldReadPeople() {

		List<Person> people = rest.get().uri("/people").exchange() //
				.expectBodyList(Person.class) //
				.returnResult().getResponseBody();

		assertThat(people.size()).isGreaterThanOrEqualTo(3);
	}

	/**
	 * Read a collection of {@link Person}.
	 */
	@Test
	public void shouldWritePerson() {

		Person person = new Person("Gus", "Fring");

		rest.put().uri("/people") //
				.syncBody(person) //
				.exchange() //
				.expectBody(Person.class);

		rest.get().uri("/people").exchange() //
				.expectBodyList(Person.class) //
				.hasSize(4);
	}
}
