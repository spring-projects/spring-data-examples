/*
 * Copyright 2015-2018 the original author or authors.
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
package example.springdata.neo4j;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simple integration test demonstrating the use of the ActorRepository
 *
 * @author Luanne Misquitta
 * @author Oliver Gierke
 * @author Michael J. Simons
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ActorRepositoryIntegrationTest {

	@SpringBootApplication
	static class ExampleConfig {
	}

	@Autowired ActorRepository actorRepository;

	@Test // see #131
	public void shouldBeAbleToSaveAndLoadActor() {

		Movie goblet = new Movie("Harry Potter and the Goblet of Fire");

		Actor daniel = new Actor("Daniel Radcliffe");
		daniel.actedIn(goblet, "Harry Potter");

		actorRepository.save(daniel); // saves the actor and the movie

		assertThat(actorRepository.findById(daniel.getId())).hasValueSatisfying(actor -> {
			assertThat(actor.getName()).isEqualTo(daniel.getName());
			assertThat(actor.getRoles()).hasSize(1).first()
				.satisfies(role -> assertThat(role.getRole()).isEqualTo("Harry Potter"));
		});
	}

	@Test // SEE #386
	public void shouldBeAbleToHandleNestedProperties() {

		Assume.assumeTrue(thatSupportForNestedPropertiesIsAvailable());

		Movie theParentTrap = new Movie("The Parent Trap");
		Movie iKnowWhoKilledMe = new Movie("I Know Who Killed Me");

		Actor lindsayLohan = new Actor("Lindsay Lohan");

		lindsayLohan.actedIn(theParentTrap, "Hallie Parker");
		lindsayLohan.actedIn(theParentTrap, "Annie James");
		lindsayLohan.actedIn(iKnowWhoKilledMe, "Aubrey Fleming");
		lindsayLohan.actedIn(iKnowWhoKilledMe, "Dakota Moss");
		actorRepository.save(lindsayLohan);

		Actor nealMcDonough = new Actor("Neal McDonough");
		nealMcDonough.actedIn(iKnowWhoKilledMe, "Daniel Fleming");
		actorRepository.save(nealMcDonough);

		assertThat(actorRepository.findAllByRolesMovieTitle(iKnowWhoKilledMe.getTitle())).hasSize(2)
			.extracting(Actor::getName).contains(lindsayLohan.getName(), nealMcDonough.getName());
	}

	private static boolean thatSupportForNestedPropertiesIsAvailable() {

		Optional<String> version = Optional.ofNullable(SpringBootVersion.getVersion());
		return version.map(v -> v.split("\\."))
			.filter(c -> c.length >= 3)
			.map(v -> new Integer[] { Integer.valueOf(v[0]), Integer.valueOf(v[1]), Integer.valueOf(v[2]) })
			.map(c -> c[0] >= 2 && (c[1] >= 1 || c[1] == 0 && c[2] >= 5))
			.orElseGet(ActorRepositoryIntegrationTest::boot210Or205SpecificMethodExists);
	}

	private static boolean boot210Or205SpecificMethodExists() {

		boolean methodExistsSince21 = false;
		try {
			methodExistsSince21 =
				SpringBootApplication.class.getMethod("setAllowBeanDefinitionOverriding", boolean.class)
					!= null;
			// Something similar for 2.0.5 need to be defined?
		} catch (NoSuchMethodException e) {
		}
		return methodExistsSince21;
	}
}
