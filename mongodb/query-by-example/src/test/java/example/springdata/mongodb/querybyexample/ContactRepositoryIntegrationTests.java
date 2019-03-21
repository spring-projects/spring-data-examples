/*
 * Copyright 2016-2018 the original author or authors.
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
package example.springdata.mongodb.querybyexample;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.domain.ExampleMatcher.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories for a case
 * where two domain types are stored in one collection.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @soundtrack Paul van Dyk - VONYC Sessions Episode 496 with guest Armin van Buuren
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactRepositoryIntegrationTests {

	@Autowired UserRepository userRepository;
	@Autowired ContactRepository contactRepository;
	@Autowired MongoOperations mongoOperations;

	Person skyler, walter, flynn;
	Relative marie, hank;

	@Before
	public void setUp() {

		contactRepository.deleteAll();

		this.skyler = contactRepository.save(new Person("Skyler", "White", 45));
		this.walter = contactRepository.save(new Person("Walter", "White", 50));
		this.flynn = contactRepository.save(new Person("Walter Jr. (Flynn)", "White", 17));
		this.marie = contactRepository.save(new Relative("Marie", "Schrader", 38));
		this.hank = contactRepository.save(new Relative("Hank", "Schrader", 43));
	}

	/**
	 * @see #153
	 */
	@Test
	public void countByConcreteSubtypeExample() {

		Example<Person> example = Example.of(new Person(null, null, null));

		assertThat(userRepository.count(example), is(3L));
	}

	/**
	 * @see #153
	 */
	@Test
	public void findAllPersonsBySimpleExample() {

		Example<Person> example = Example.of(new Person(".*", null, null), //
				matching().withStringMatcher(StringMatcher.REGEX));

		assertThat(userRepository.findAll(example), containsInAnyOrder(skyler, walter, flynn));
		assertThat(userRepository.findAll(example), not(containsInAnyOrder(hank, marie)));
	}

	/**
	 * @see #153
	 */
	@Test
	public void findAllRelativesBySimpleExample() {

		Example<Relative> example = Example.of(new Relative(".*", null, null), //
				matching().withStringMatcher(StringMatcher.REGEX));

		assertThat(contactRepository.findAll(example), containsInAnyOrder(hank, marie));
		assertThat(contactRepository.findAll(example), not(containsInAnyOrder(skyler, walter, flynn)));
	}
}
