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
import static org.junit.Assume.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Version;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

/**
 * Simple integration test demonstrating the use of the ActorRepository
 *
 * @author Luanne Misquitta
 * @author Oliver Gierke
 * @author Michael J. Simons
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ActorRepositoryIntegrationTest {

	@SpringBootApplication
	static class ExampleConfig {}

	@Autowired ActorRepository actorRepository;

	@Test // #131
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

	@Test // #386
	public void shouldBeAbleToHandleNestedProperties() {

		assumeTrue(thatSupportForNestedPropertiesIsAvailable());

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

		Version minVersion = Version.parse("2.0.5");

		return Optional.ofNullable(SpringBootVersion.getVersion()).map(Version::parse) //
				.map(v -> v.isGreaterThanOrEqualTo(minVersion)) //
				.orElseGet(ActorRepositoryIntegrationTest::fallBackToVersionSpecificClasses);
	}

	private static boolean fallBackToVersionSpecificClasses() {

		ClassLoader usedClassLoader = ActorRepositoryIntegrationTest.class.getClassLoader();

		String fqnBoot210Class = "org.springframework.boot.autoconfigure.insight.InsightsProperties";
		String fqnBoot205Class = "org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider";

		return ClassUtils.isPresent(fqnBoot210Class, usedClassLoader) //
				|| ClassUtils.isPresent(fqnBoot205Class, usedClassLoader);
	}
}
