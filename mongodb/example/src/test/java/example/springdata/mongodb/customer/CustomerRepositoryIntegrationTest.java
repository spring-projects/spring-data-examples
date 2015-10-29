/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.mongodb.customer;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.querydsl.QSort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test for {@link CustomerRepository}.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
public class CustomerRepositoryIntegrationTest {

	@Autowired CustomerRepository repository;
	@Autowired MongoOperations operations;

	Customer dave, oliver, carter;

	@Before
	public void setUp() {

		repository.deleteAll();

		dave = repository.save(new Customer("Dave", "Matthews"));
		oliver = repository.save(new Customer("Oliver August", "Matthews"));
		carter = repository.save(new Customer("Carter", "Beauford"));
	}

	/**
	 * Test case to show that automatically generated ids are assigned to the domain objects.
	 */
	@Test
	public void setsIdOnSave() {

		Customer dave = repository.save(new Customer("Dave", "Matthews"));
		assertThat(dave.getId(), is(notNullValue()));
	}

	/**
	 * Test case to show the usage of the Querydsl-specific {@link QSort} to define the sort order in a type-safe way.
	 */
	@Test
	public void findCustomersUsingQuerydslSort() {

		QCustomer customer = QCustomer.customer;
		List<Customer> result = repository.findByLastname("Matthews", new QSort(customer.firstname.asc()));

		assertThat(result, hasSize(2));
		assertThat(result.get(0), is(dave));
		assertThat(result.get(1), is(oliver));
	}

	/**
	 * Test case to show the usage of the geo-spatial APIs to lookup people within a given distance of a reference point.
	 */
	@Test
	public void exposesGeoSpatialFunctionality() {

		GeospatialIndex indexDefinition = new GeospatialIndex("address.location");
		indexDefinition.getIndexOptions().put("min", -180);
		indexDefinition.getIndexOptions().put("max", 180);

		operations.indexOps(Customer.class).ensureIndex(indexDefinition);

		Customer ollie = new Customer("Oliver", "Gierke");
		ollie.setAddress(new Address(new Point(52.52548, 13.41477)));
		ollie = repository.save(ollie);

		Point referenceLocation = new Point(52.51790, 13.41239);
		Distance oneKilometer = new Distance(1, Metrics.KILOMETERS);

		GeoResults<Customer> result = repository.findByAddressLocationNear(referenceLocation, oneKilometer);

		assertThat(result.getContent(), hasSize(1));

		Distance distanceToFirstStore = result.getContent().get(0).getDistance();
		assertThat(distanceToFirstStore.getMetric(), is(Metrics.KILOMETERS));
		assertThat(distanceToFirstStore.getValue(), closeTo(0.862, 0.001));
	}
}
