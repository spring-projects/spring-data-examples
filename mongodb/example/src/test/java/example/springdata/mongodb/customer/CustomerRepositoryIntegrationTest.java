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
package example.springdata.mongodb.customer;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Offset.offset;

import example.springdata.mongodb.util.MongoContainers;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.querydsl.QSort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration test for {@link CustomerRepository}.
 *
 * @author Oliver Gierke
 */
@Testcontainers
@DataMongoTest
class CustomerRepositoryIntegrationTest {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired CustomerRepository repository;
	@Autowired MongoOperations operations;

	private Customer dave, oliver, carter;

	@BeforeEach
	void setUp() {

		repository.deleteAll();

		dave = repository.save(new Customer("Dave", "Matthews"));
		oliver = repository.save(new Customer("Oliver August", "Matthews"));
		carter = repository.save(new Customer("Carter", "Beauford"));
	}

	/**
	 * Test case to show that automatically generated ids are assigned to the domain objects.
	 */
	@Test
	void setsIdOnSave() {

		var dave = repository.save(new Customer("Dave", "Matthews"));
		assertThat(dave.getId()).isNotNull();
	}

	/**
	 * Test case to show the usage of the Querydsl-specific {@link QSort} to define the sort order in a type-safe way.
	 */
	@Test
	void findCustomersUsingQuerydslSort() {

		var customer = QCustomer.customer;
		var result = repository.findByLastname("Matthews", new QSort(customer.firstname.asc()));

		assertThat(result).hasSize(2);
		assertThat(result.get(0)).isEqualTo(dave);
		assertThat(result.get(1)).isEqualTo(oliver);
	}

	/**
	 * Test case to show the usage of Java {@link Stream}.
	 */
	@Test
	void findCustomersAsStream() {

		try (var result = repository.findAllByCustomQueryWithStream()) {
			result.forEach(System.out::println);
		}
	}

	/**
	 * Test case to show the usage of the geo-spatial APIs to lookup people within a given distance of a reference point.
	 */
	@Test
	void exposesGeoSpatialFunctionality() {

		var indexDefinition = new GeospatialIndex("address.location");
		indexDefinition.getIndexOptions().put("min", -180);
		indexDefinition.getIndexOptions().put("max", 180);

		operations.indexOps(Customer.class).ensureIndex(indexDefinition);

		var ollie = new Customer("Oliver", "Gierke");
		ollie.setAddress(new Address(new Point(52.52548, 13.41477)));
		ollie = repository.save(ollie);

		var referenceLocation = new Point(52.51790, 13.41239);
		var oneKilometer = new Distance(1, Metrics.KILOMETERS);

		var result = repository.findByAddressLocationNear(referenceLocation, oneKilometer);

		assertThat(result.getContent()).hasSize(1);

		var distanceToFirstStore = result.getContent().get(0).getDistance();
		assertThat(distanceToFirstStore.getMetric()).isEqualTo(Metrics.KILOMETERS);
		assertThat(distanceToFirstStore.getValue()).isCloseTo(0.862, offset(0.001));
	}
}
