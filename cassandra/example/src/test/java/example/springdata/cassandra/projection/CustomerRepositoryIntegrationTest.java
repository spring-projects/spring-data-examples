/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.cassandra.projection;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.projection.TargetAware;

/**
 * Integration tests for {@link CustomerRepository} to show projection capabilities.
 *
 * @author Mark Paluch
 */
@SpringBootTest(classes = ProjectionConfiguration.class)
@CassandraKeyspace
class CustomerRepositoryIntegrationTest {

	@Autowired CustomerRepository customers;

	private Customer dave, carter;

	@BeforeEach
	void setUp() {

		customers.deleteAll();

		this.dave = customers.save(new Customer("d", "Dave", "Matthews"));
		this.carter = customers.save(new Customer("c", "Carter", "Beauford"));
	}

	@Test
	void projectsEntityIntoInterface() {

		var result = customers.findAllProjectedBy();

		assertThat(result).hasSize(2);
		assertThat(result.iterator().next().getFirstname()).isEqualTo("Carter");
	}

	@Test
	void projectsDynamically() {

		var result = customers.findById("d", CustomerProjection.class);

		assertThat(result).hasSize(1);
		assertThat(result.iterator().next().getFirstname()).isEqualTo("Dave");
	}

	@Test
	void projectsIndividualDynamically() {

		var result = customers.findProjectedById(dave.id(), CustomerSummary.class);

		assertThat(result).isNotNull();
		assertThat(result.getFullName()).isEqualTo("Dave Matthews");

		// Proxy backed by original instance as the projection uses dynamic elements
		assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer.class);
	}

	@Test
	void projectIndividualInstance() {

		var result = customers.findProjectedById(dave.id());

		assertThat(result).isNotNull();
		assertThat(result.getFirstname()).isEqualTo("Dave");
		assertThat(((TargetAware) result).getTarget()).isInstanceOf(Customer.class);
	}
}
