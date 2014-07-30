package jpaandmongodb;

import javax.annotation.PostConstruct;

import jpaandmongodb.jpa.Person;
import jpaandmongodb.jpa.PersonRepository;
import jpaandmongodb.mongodb.Treasure;
import jpaandmongodb.mongodb.TreasureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = Person.class)
@EnableMongoRepositories(basePackageClasses = Treasure.class)
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Autowired
	PersonRepository personRepository;

	@Autowired
	TreasureRepository treasureRepository;

	@PostConstruct
	void checkitOut() {
		personRepository.save(new Person("Frodo", "Baggins"));
		personRepository.save(new Person("Bilbo", "Baggins"));

		for (Person person : personRepository.findAll()) {
			log.info("Hello " + person.toString());
		}

		treasureRepository.deleteAll();

		Treasure sting = new Treasure();
		sting.setName("Sting");
		sting.setDescription("Made by the Elves");
		treasureRepository.save(sting);

		Treasure ring = new Treasure();
		ring.setName("Sauron's ring");
		ring.setDescription("One ring to rule them all");
		treasureRepository.save(ring);

		for (Treasure treasure : treasureRepository.findAll()) {
			log.info("Found treasure " + treasure.toString());
		}
	}
}
