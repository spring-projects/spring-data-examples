/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import example.springdata.map.Person;
import example.springdata.map.PersonRepository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for {@link PersonRepository}.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class PersonRepositoryIntegrationTest {

	@Configuration
	@EnableMapRepositories
	static class Config {}

	@Autowired PersonRepository repository;

	@Test
	public void storesPerson() {

		Person person = repository.save(new Person("Dave", "Matthews", 47));

		assertThat(repository.findOne(person.getId()), is(person));
	}

	@Test
	public void findsPersonByAge() {

		Person dave = repository.save(new Person("Dave", "Matthews", 47));
		Person oliver = repository.save(new Person("Oliver August", "Matthews", 7));

		List<Person> result = repository.findByAgeGreaterThan(18);

		assertThat(result, hasItem(dave));
		assertThat(result, not(hasItem(oliver)));
	}
}
