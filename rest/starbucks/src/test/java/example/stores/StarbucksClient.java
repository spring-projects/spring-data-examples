/*
 * Copyright 2014-2015 the original author or authors.
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
package example.stores;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.client.Traverson.TraversalBuilder;
import org.springframework.hateoas.mvc.TypeReferences.PagedResourcesType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A test case to discover the search resource and execute a predefined search with it.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
@Slf4j
public class StarbucksClient {

	@Configuration
	static class Config {}

	@Test
	@Ignore
	public void discoverStoreSearch() {

		Traverson traverson = new Traverson(URI.create("http://localhost:8080"), MediaTypes.HAL_JSON);

		// Set up path traversal
		TraversalBuilder builder = traverson. //
				follow("stores", "search", "by-location");

		// Log discovered
		log.info("");
		log.info("Discovered link: {}", builder.asTemplatedLink());
		log.info("");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("location", "40.740337,-73.995146");
		parameters.put("distance", "0.5miles");

		PagedResources<Resource<Store>> resources = builder.//
				withTemplateParameters(parameters).//
				toObject(new PagedResourcesType<Resource<Store>>() {});

		PageMetadata metadata = resources.getMetadata();

		log.info("Got {} of {} stores: ", resources.getContent().size(), metadata.getTotalElements());

		StreamSupport.stream(resources.spliterator(), false).//
				map(Resource::getContent).//
				forEach(store -> log.info("{} - {}", store.name, store.address));
	}

	static class Store {

		public String name;
		public Address address;

		static class Address {

			public String city, zip, street;
			public Location location;

			@Override
			public String toString() {
				return String.format("%s, %s %s - lat: %s, long: %s", street, zip, city, location.y, location.x);
			}

			static class Location {
				public double x, y;
			}
		}
	}
}
