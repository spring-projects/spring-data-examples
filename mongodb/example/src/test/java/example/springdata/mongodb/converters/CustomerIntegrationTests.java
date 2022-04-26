/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.mongodb.converters;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.util.MongoContainers;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration tests for {@link Customer} conversions.
 *
 * @author Mark Paluch
 */
@Testcontainers
@DataMongoTest
class CustomerIntegrationTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired MongoOperations operations;

	@BeforeEach
	void setUp() {
		operations.remove(Customer.class).all();
	}

	/**
	 * Applies {@link org.springframework.data.mongodb.core.convert.MongoValueConverter} to individual properties.
	 * {@code primary} and {@code secondary} are configured with different converters of which the first one uses JSON
	 * conversion and the second converter interacts directly with a MongoDB {@link Document}.
	 */
	@Test
	void appliesConverters() {

		var primaryAddress = new Address(new Point(4.1, 5.6));
		primaryAddress.setStreet("308 Negra Arroyo Lane");
		primaryAddress.setZipCode("87104");

		var secondaryAddress = new Address(new Point(6.1, 7.6));
		secondaryAddress.setStreet("3828 Piermont Drive");
		secondaryAddress.setZipCode("87111");

		var customer = new Customer("Walter", "White");
		customer.setPrimary(primaryAddress);
		customer.setSecondary(secondaryAddress);

		operations.insert(customer);

		Document document = operations.findOne(new Query(), Document.class, "customer");

		assertThat(document.get("primary"))
				.isEqualTo("{\"location\":{\"x\":4.1,\"y\":5.6},\"street\":\"308 Negra Arroyo Lane\",\"zipCode\":\"87104\"}");
		assertThat(document.get("secondary")).isEqualTo(
				new Document("x", secondaryAddress.getLocation().getX()).append("y", secondaryAddress.getLocation().getY()));

		Customer loadedCustomer = operations.findOne(new Query(), Customer.class, "customer");

		// Primary address marshalled as JSON
		assertThat(loadedCustomer.getPrimary()).isNotNull();
		assertThat(loadedCustomer.getPrimary().getStreet()).isEqualTo(primaryAddress.getStreet());
		assertThat(loadedCustomer.getPrimary().getZipCode()).isEqualTo(primaryAddress.getZipCode());
		assertThat(loadedCustomer.getPrimary().getLocation()).isEqualTo(primaryAddress.getLocation());

		// Secondary address stores only location as the converter considers only points
		assertThat(loadedCustomer.getSecondary()).isNotNull();
		assertThat(loadedCustomer.getSecondary().getStreet()).isNull();
		assertThat(loadedCustomer.getSecondary().getLocation()).isEqualTo(secondaryAddress.getLocation());

	}
}
