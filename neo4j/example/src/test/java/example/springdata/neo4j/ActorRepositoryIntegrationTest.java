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

package example.springdata.neo4j;

import static org.junit.Assert.*;

import example.springdata.neo4j.domain.Actor;
import example.springdata.neo4j.domain.Movie;
import example.springdata.neo4j.domain.Role;
import example.springdata.neo4j.repo.ActorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Simple integration test demonstrating the use of the ActorRepository
 * @author Luanne Misquitta
 */
@ContextConfiguration(classes = {ExampleConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ActorRepositoryIntegrationTest {

	@Autowired ActorRepository actorRepository;

	/**
	 * @see DATAGRAPH-752
	 * Test case to demonstrate saving node and relationship entities, and loading them via the repository.
	 */
	@Test
	public void shouldBeAbleToSaveAndLoadActor() {

		Actor daniel  = new Actor("Daniel Radcliffe");
		Movie goblet = new Movie("Harry Potter and the Goblet of Fire");
		daniel.actedIn(goblet,"Harry Potter");
		actorRepository.save(daniel); //saves the actor and the movie


		Actor actor = actorRepository.findOne(daniel.getId());
		assertNotNull(actor);
		assertEquals(daniel.getName(),actor.getName());
		assertEquals(1,actor.getRoles().size());
		Role role = actor.getRoles().iterator().next();
		assertEquals("Harry Potter", role.getRole());
	}

}
