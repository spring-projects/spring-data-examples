/*
 * Copyright 2014-2024 the original author or authors.
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
package example.springdata.rest.stores;

import static org.springframework.hateoas.MediaTypes.*;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.hateoas.server.core.TypeReferences.EntityModelType;
import org.springframework.hateoas.server.core.TypeReferences.PagedModelType;
import org.springframework.web.client.RestClient;

/**
 * A test case to discover the search resource and execute a predefined search with it.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StarbucksClient {

	@LocalServerPort int port;
	@Autowired TestRestTemplate template;

	private static final String SERVICE_URI = "http://localhost:%s/api";

	@Test
	void discoverStoreSearch() {

		var traverson = new Traverson(URI.create(String.format(SERVICE_URI, port)), MediaTypes.HAL_JSON);

		// Set up path traversal
		var builder = traverson. //
				follow("stores", "search", "by-location");

		// Log discovered
		log.info("");
		log.info("Discovered link: {}", builder.asTemplatedLink());
		log.info("");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("location", "40.740337,-73.995146");
		parameters.put("distance", "0.5miles");

		var resources = builder.//
				withTemplateParameters(parameters).//
				toObject(new PagedModelType<EntityModel<Store>>() {});

		var metadata = resources.getMetadata();

		log.info("Got {} of {} stores: ", resources.getContent().size(), metadata.getTotalElements());

		StreamSupport.stream(resources.spliterator(), false).//
				map(EntityModel::getContent).//
				forEach(store -> log.info("{} - {}", store.name, store.address));
	}

	@Test
	void accessServiceUsingRestTemplate() {

		// Access root resource

		var client = RestClient.create(template.getRestTemplate());

		var links = client.get()
				.uri(URI.create(String.format(SERVICE_URI, port)))
				.accept(HAL_JSON)
				.retrieve()
				.body(new EntityModelType<>() {})
				.getLinks();

		// Follow stores link

		var storesLink = links.getRequiredLink("stores").expand();

		var stores = client.get().uri(storesLink.toUri())
				.accept(HAL_JSON)
				.retrieve()
				.body(new CollectionModelType<Store>() {});

		stores.getContent().forEach(store -> log.info("{} - {}", store.name, store.address));
	}

	record Store(String name, StarbucksClient.Store.Address address) {

		record Address(String city, String zip, String street, Store.Address.Location location) {

			@Override
			public String toString() {
				return String.format("%s, %s %s - lat: %s, long: %s", street, zip, city, location.y, location.x);
			}

			record Location(double x, double y) {}
		}
	}
}
