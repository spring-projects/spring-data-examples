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

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.TypeReferences.PagedResourcesType;

/**
 * @author Oliver Gierke
 */
public class StarbucksClient {

	public static void main(String[] args) {

		Traverson traverson = new Traverson(URI.create("http://localhost:8080"), MediaTypes.HAL_JSON);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("location", "40.740337,-73.995146");
		parameters.put("distance", "0.5miles");

		PagedResources<Resource<Store>> resources = traverson. //
				follow("stores", "search", "by-location").//
				withTemplateParameters(parameters).//
				toObject(new PagedResourcesType<Resource<Store>>() {});

		PageMetadata metadata = resources.getMetadata();

		System.out.println(String.format("Got %s of %s stores: ", resources.getContent().size(),
				metadata.getTotalElements()));

		StreamSupport.stream(resources.spliterator(), false).//
				map(Resource::getContent).//
				forEach(store -> String.format("- %s - %s", store.name, store.address));
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
