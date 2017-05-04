/*
 * Copyright 2015-2016 the original author or authors.
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
package example.springdata.jpa.projections;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.projection.TargetAware;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link CustomerRepository} to show projection capabilities.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class CustomerRepositoryIntegrationTest {

	@Configuration
	@EnableAutoConfiguration
	static class Config {}

	@Autowired CustomerRepository customers;

	Customer dave, carter;

	@Before
	public void setUp() {

		this.dave = customers.save(new Customer("Dave", "Matthews"));
		this.carter = customers.save(new Customer("Carter", "Beauford"));
	}

	@Test
	public void projectsEntityIntoInterface() {

		assertThat(customers.findAllProjectedBy())//
				.hasSize(2)//
				.first().satisfies(it -> assertThat(it.getFirstname()).isEqualTo("Dave"));
	}

	@Test
	public void projectsMapIntoInterface() {

		assertThat(customers.findsByProjectedColumns())//
				.hasSize(2)//
				.first().satisfies(it -> assertThat(it.getFirstname()).isEqualTo("Dave"));

	}

	@Test
	public void projectsToDto() {

		assertThat(customers.findAllDtoedBy())//
				.hasSize(2)//
				.first().satisfies(it -> assertThat(it.getFirstname()).isEqualTo("Dave"));
	}

	@Test
	public void projectsDynamically() {

		assertThat(customers.findByFirstname("Dave", CustomerProjection.class))//
				.hasSize(1)//
				.first()//
				.satisfies(it -> assertThat(it.getFirstname()).isEqualTo("Dave"));
	}

	@Test
	public void projectsIndividualDynamically() {

		CustomerSummary result = customers.findProjectedById(dave.getId(), CustomerSummary.class);

		assertThat(result.getFullName()).isEqualTo("Dave Matthews");

		// Proxy backed by original instance as the projection uses dynamic elements
		assertThat(result).isInstanceOfSatisfying(TargetAware.class,
				it -> assertThat(it.getTarget()).isInstanceOf(Customer.class));
	}

	@Test
	public void projectIndividualInstance() {

		CustomerProjection projectedDave = customers.findProjectedById(dave.getId());

		assertThat(projectedDave.getFirstname()).isEqualTo("Dave");
		assertThat(projectedDave).isInstanceOfSatisfying(TargetAware.class,
				it -> assertThat(it.getTarget()).isInstanceOf(Map.class));
	}

	@Test
	public void projectsDtoUsingConstructorExpression() {

		Collection<CustomerDto> result = customers.findDtoWithConstructorExpression("Dave");

		assertThat(result).hasSize(1);
		assertThat(result.iterator().next().getFirstname()).isEqualTo("Dave");
	}

	@Test
	public void supportsProjectionInCombinationWithPagination() {

		Page<CustomerProjection> page = customers
				.findPagedProjectedBy(PageRequest.of(0, 1, Sort.by(Direction.ASC, "lastname")));

		assertThat(page.getContent().get(0).getFirstname()).isEqualTo("Carter");
	}

	@Test
	public void appliesProjectionToOptional() {
		assertThat(customers.findOptionalProjectionByLastname("Beauford")).isPresent();
	}
}
