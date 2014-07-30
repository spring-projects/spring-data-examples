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
package example;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import example.person.Person;
import example.person.PersonRepository;
import example.treasure.Treasure;
import example.treasure.TreasureRepository;

/**
 * Application configuration file. Used for bootstrap and data setup.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableMongoRepositories
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired PersonRepository personRepository;
	@Autowired TreasureRepository treasureRepository;

	@PostConstruct
	void checkitOut() {

		personRepository.save(new Person("Frodo", "Baggins"));
		personRepository.save(new Person("Bilbo", "Baggins"));

		for (Person person : personRepository.findAll()) {
			log.info("Hello " + person.toString());
		}

		treasureRepository.deleteAll();
		treasureRepository.save(new Treasure("Sting", "Made by the Elves"));
		treasureRepository.save(new Treasure("Sauron's ring", "One ring to rule them all"));

		for (Treasure treasure : treasureRepository.findAll()) {
			log.info("Found treasure " + treasure.toString());
		}
	}
}
