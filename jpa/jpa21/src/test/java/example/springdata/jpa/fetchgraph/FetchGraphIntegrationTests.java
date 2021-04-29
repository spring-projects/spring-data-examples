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
package example.springdata.jpa.fetchgraph;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import javax.persistence.EntityManager;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the usage of JPA 2.1 fetch graph support through Spring Data JPA repositories.
 *
 * @author Thomas Darimont
 * @author Jens Schauder
 * @author Divya Srivastava
 */
@Transactional
@SpringBootTest(classes = FetchGraphConfiguration.class)
class FetchGraphIntegrationTests {

	@Autowired EntityManager em;

	@Autowired ProductRepository repository;

	@Test
	void shouldFetchAssociationMarkedAsLazyViaNamedEntityFetchGraph() {

		var xps = new Product("Dell XPS 15");
		Collections.addAll(xps.getTags(), new Tag("cool"), new Tag("macbook-killer"), new Tag("speed"));

		xps = repository.save(xps);
		repository.flush();

		em.detach(xps);

		var loadedXps = repository.findById(xps.getId()).get();
		em.detach(loadedXps);

		assertThatExceptionOfType(LazyInitializationException.class)
				.as("Expected LazyInitializationException to occur when trying to access uninitialized association 'tags'.")
				.isThrownBy(() -> loadedXps.getTags().toString());

		// here we use the findWithNamedEntityGraphById that uses a NamedEntityGraph
		var loadedXpsWithFetchGraph = repository.findWithNamedEntityGraphById(xps.getId());

		assertThat(loadedXpsWithFetchGraph.getTags()).hasSize(3);
	}

	@Test
	void shouldFetchAssociationMarkedAsLazyViaCustomEntityFetchGraph() {

		var xps = new Product("Dell XPS 15");
		Collections.addAll(xps.getTags(), new Tag("cool"), new Tag("macbook-killer"), new Tag("speed"));

		xps = repository.save(xps);
		repository.flush();

		em.detach(xps);

		var loadedXps = repository.findById(xps.getId()).get();
		em.detach(loadedXps);

		assertThatExceptionOfType(LazyInitializationException.class)
				.as("Expected LazyInitializationException to occur when trying to access uninitialized association 'tags'.")
				.isThrownBy(() -> loadedXps.getTags().toString());

		// here we use findWithAdhocEntityGraphById which uses an ad-hoc declarative fetch graph definition
		var loadedXpsWithFetchGraph = repository.findWithAdhocEntityGraphById(xps.getId());

		assertThat(loadedXpsWithFetchGraph.getTags()).hasSize(3);
	}
}
