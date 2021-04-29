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
package example.springdata.neo4j;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.Neo4jContainer;

/**
 * Simple integration test demonstrating the use of the ActorRepository
 *
 * @author Luanne Misquitta
 * @author Oliver Gierke
 * @author Michael J. Simons
 * @author Mark Paluch
 */
@SpringBootTest
class ActorRepositoryIntegrationTest {

	private static Neo4jContainer container;

	@BeforeAll
	static void stopNeo4j() {

		container = new Neo4jContainer("neo4j:4.2.5");
		container.start();
	}

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.neo4j.uri", container::getBoltUrl);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", container::getAdminPassword);
	}

	@SpringBootApplication
	static class ExampleConfig {
	}

	@Autowired ActorRepository actorRepository;

	@Test // #131
	void shouldBeAbleToSaveAndLoadActor() {

		var goblet = new Movie("Harry Potter and the Goblet of Fire");

		var daniel = new Actor("Daniel Radcliffe");
		daniel.actedIn(goblet, Collections.singletonList("Harry Potter"));

		actorRepository.save(daniel); // saves the actor and the movie

		assertThat(actorRepository.findById(daniel.getId())).hasValueSatisfying(actor -> {
			assertThat(actor.getName()).isEqualTo(daniel.getName());
			assertThat(actor.getRoles()).hasSize(1)
				.satisfies(roles -> assertThat(roles).flatExtracting(Roles::getRoles)
					.hasSize(1).first()
					.isEqualTo("Harry Potter"));
		});
	}

	@Test // #386
	void shouldBeAbleToHandleNestedProperties() {

		var theParentTrap = new Movie("The Parent Trap");
		var iKnowWhoKilledMe = new Movie("I Know Who Killed Me");

		var lindsayLohan = new Actor("Lindsay Lohan");

		lindsayLohan.actedIn(theParentTrap, Arrays.asList("Hallie Parker","Annie James"));
		lindsayLohan.actedIn(iKnowWhoKilledMe, Arrays.asList("Aubrey Fleming", "Dakota Moss"));
		actorRepository.save(lindsayLohan);

		var nealMcDonough = new Actor("Neal McDonough");
		nealMcDonough.actedIn(iKnowWhoKilledMe, Collections.singletonList("Daniel Fleming"));
		actorRepository.save(nealMcDonough);

		assertThat(actorRepository.findAllByRolesMovieTitle(iKnowWhoKilledMe.getTitle())).hasSize(2)
			.satisfies(actors -> assertThat(actors).extracting(Actor::getName)
				.contains(lindsayLohan.getName(), nealMcDonough.getName()))
			.allSatisfy(actor -> {
				if (actor.getName().equals(nealMcDonough.getName())) {
					assertThat(actor.getRoles())
						.allSatisfy((r) -> assertThat(r.getRoles()).containsOnly("Daniel Fleming"));
				} else if (actor.getName().equals(lindsayLohan.getName())) {
					assertThat(actor.getRoles())
						.allSatisfy((r) -> assertThat(r.getRoles()).containsOnly("Aubrey Fleming", "Dakota Moss"));
				}
			});
	}
}
