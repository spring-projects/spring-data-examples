/*
 * Copyright 2016 the original author or authors.
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

package example.springdata.mongodb.querybyexample;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleSpec;
import org.springframework.data.domain.ExampleSpec.StringMatcher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test showing the usage of MongoDB Query-by-Example support through Spring Data repositories for a case where two domain types are stored in one collection.
 *
 * @author Mark Paluch
 * @soundtrack Paul van Dyk - VONYC Sessions Episode 496 with guest Armin van Buuren
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
public class ContactRepositoryIntegrationTests {

	@Autowired UserRepository userRepository;
	@Autowired ContactRepository contactRepository;
	@Autowired MongoOperations mongoOperations;

	User skyler, walter, flynn;
	Contact marie, hank;

	@Before
	public void setUp() {

		userRepository.deleteAll();

		this.skyler = userRepository.save(new User("Skyler", "White", 45));
		this.walter = userRepository.save(new User("Walter", "White", 50));
		this.flynn = userRepository.save(new User("Walter Jr. (Flynn)", "White", 17));
		this.marie = contactRepository.save(new Contact("Marie", "Schrader", 38));
		this.hank = contactRepository.save(new Contact("Hank", "Schrader", 43));
	}

	/**
	 * @see DATAMONGO-1245
	 */
	@Test
	public void countUsersByUntypedExample() {

		Example<User> example = Example.of(new User(null, null, null));

		// the count is 5 because untyped examples do not restrict the type
		assertThat(userRepository.count(example), is(5L));
		assertThat(mongoOperations.count(new Query(), "collectionStoringTwoTypes"), is(5L));
	}

	/**
	 * @see DATAMONGO-1245
	 */
	@Test
	public void countByTypedExample() {

		Example<User> example = Example.of(new User(null, null, null), ExampleSpec.typed(User.class));

		assertThat(userRepository.count(example), is(3L));
	}

	/**
	 * @see DATAMONGO-1245
	 */
	@Test
	public void findAllUsersBySimpleExample() {

		Example<User> example = Example.of(new User(".*", null, null), //
				ExampleSpec.typed(User.class).withStringMatcher(StringMatcher.REGEX));

		assertThat(userRepository.findAll(example), containsInAnyOrder(skyler, walter, flynn));
		assertThat(userRepository.findAll(example), not(containsInAnyOrder(hank, marie)));
	}

	/**
	 * @see DATAMONGO-1245
	 */
	@Test
	public void findAllContactsBySimpleExample() {

		Example<Contact> example = Example.of(new Contact(".*", null, null), //
				ExampleSpec.typed(Contact.class).withStringMatcher(StringMatcher.REGEX));

		assertThat(contactRepository.findAll(example), containsInAnyOrder(hank, marie));
		assertThat(contactRepository.findAll(example), not(containsInAnyOrder(skyler, walter, flynn)));
	}

}
