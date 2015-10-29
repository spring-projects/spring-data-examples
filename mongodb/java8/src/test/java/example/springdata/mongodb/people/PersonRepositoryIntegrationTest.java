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
package example.springdata.mongodb.people;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test for {@link PersonRepository}.
 * 
 * @author Thomas Darimont
 * @authot Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
public class PersonRepositoryIntegrationTest {

	@Autowired PersonRepository repository;
	@Autowired MongoOperations operations;

	Person dave, oliver, carter;

	@Before
	public void setUp() {

		repository.deleteAll();

		dave = repository.save(new Person("Dave", "Matthews"));
		oliver = repository.save(new Person("Oliver August", "Matthews"));
		carter = repository.save(new Person("Carter", "Beauford"));
	}

	/**
	 * Note that the all object conversions are preformed before the results are printed to the console.
	 */
	@Test
	public void shouldPerformConversionBeforeResultProcessing() {
		repository.findAll().forEach(System.out::println);
	}

	/**
	 * Note that the object conversions are preformed during stream processing as one can see from the
	 * {@link LoggingEventListener} output that is printed to the console.
	 */
	@Test
	public void shouldPerformConversionDuringJava8StreamProcessing() {

		try (Stream<Person> result = repository.findAllByCustomQueryWithStream()) {
			result.forEach(System.out::println);
		}
	}
}
