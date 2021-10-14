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
package example.springdata.mongodb.projections;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.util.MongoContainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.projection.TargetAware;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration tests for {@link CustomerRepository} to show projection capabilities.
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

	@Configuration
	@EnableAutoConfiguration
	static class Config {}

	@Autowired CustomerRepository customers;

	private Customer dave, carter;

	@BeforeEach
	void setUp() {

		customers.deleteAll();

		this.dave = customers.save(new Customer("Dave", "Matthews"));
		this.carter = customers.save(new Customer("Carter", "Beauford"));
	}

	@Test
	void projectsEntityIntoInterface() {

		var result = customers.findAllProjectedBy();

		assertThat(result).hasSize(2);
		assertThat(result.iterator().next().getFirstname()).isEqualTo("Dave");
	}

	@Test
	void projectsToDto() {

		var result = customers.findAllDtoedBy();

		assertThat(result).hasSize(2);
		assertThat(result.iterator().next().firstname()).isEqualTo("Dave");
	}

	@Test
	void projectsDynamically() {

		var result = customers.findByFirstname("Dave", CustomerProjection.class);

		assertThat(result).hasSize(1);
		assertThat(result.iterator().next().getFirstname()).isEqualTo("Dave");
	}

	@Test
	void projectsIndividualDynamically() {

		var result = customers.findProjectedById(dave.getId(), CustomerSummary.class);

		assertThat(result).isNotNull();
		assertThat(result.getFullName()).isEqualTo("Dave Matthews");

		// Proxy backed by original instance as the projection uses dynamic elements
		assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer.class);
	}

	@Test
	void projectIndividualInstance() {

		var result = customers.findProjectedById(dave.getId());

		assertThat(result).isNotNull();
		assertThat(result.getFirstname()).isEqualTo("Dave");
		assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer.class);
	}

	@Test
	void supportsProjectionInCombinationWithPagination() {

		var page = customers
				.findPagedProjectedBy(PageRequest.of(0, 1, Sort.by(Direction.ASC, "lastname")));

		assertThat(page.getContent().get(0).getFirstname()).isEqualTo("Carter");
	}
}
