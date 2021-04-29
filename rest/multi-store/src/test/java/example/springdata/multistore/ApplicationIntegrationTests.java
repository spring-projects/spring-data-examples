/*
 * Copyright 2015-2021 the original author or authors.
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
package example.springdata.multistore;

import example.springdata.multistore.person.Person;
import example.springdata.multistore.person.PersonRepository;
import example.springdata.multistore.treasure.Treasure;
import example.springdata.multistore.treasure.TreasureRepository;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test to show the usage of repositories backed by different stores.
 *
 * @author Oliver Gierke
 */
@Slf4j
@SpringBootTest
public class ApplicationIntegrationTests {

	@Autowired PersonRepository personRepository;
	@Autowired TreasureRepository treasureRepository;

	@Test
	public void useMultipleRepositories() {

		personRepository.save(new Person("Frodo", "Baggins"));
		personRepository.save(new Person("Bilbo", "Baggins"));

		for (var person : personRepository.findAll()) {
			log.info("Hello " + person.toString());
		}

		treasureRepository.deleteAll();
		treasureRepository.save(new Treasure("Sting", "Made by the Elves"));
		treasureRepository.save(new Treasure("Sauron's ring", "One ring to rule them all"));

		for (var treasure : treasureRepository.findAll()) {
			log.info("Found treasure " + treasure.toString());
		}
	}
}
