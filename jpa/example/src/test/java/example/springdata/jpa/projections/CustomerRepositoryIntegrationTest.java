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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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

		Collection<CustomerProjection> result = customers.findAllProjectedBy();

		assertThat(result, hasSize(2));
		assertThat(result.iterator().next().getFirstname(), is("Dave"));
	}

	@Test
	public void projectsMapIntoInterface() {

		Collection<CustomerProjection> result = customers.findsByProjectedColumns();

		assertThat(result, hasSize(2));
		assertThat(result.iterator().next().getFirstname(), is("Dave"));
	}

	@Test
	public void projectsToDto() {

		Collection<CustomerDto> result = customers.findAllDtoedBy();

		assertThat(result, hasSize(2));
		assertThat(result.iterator().next().getFirstname(), is("Dave"));
	}

	@Test
	public void projectsDynamically() {

		Collection<CustomerProjection> result = customers.findByFirstname("Dave", CustomerProjection.class);

		assertThat(result, hasSize(1));
		assertThat(result.iterator().next().getFirstname(), is("Dave"));
	}

	@Test
	public void projectsIndividualDynamically() {

		CustomerSummary result = customers.findProjectedById(dave.getId(), CustomerSummary.class);

		assertThat(result.getFullName(), is("Dave Matthews"));

		// Proxy backed by original instance as the projection uses dynamic elements
		assertThat(((TargetAware) result).getTarget(), is(instanceOf(Customer.class)));
	}

	@Test
	public void projectIndividualInstance() {

		CustomerProjection projectedDave = customers.findProjectedById(dave.getId());

		assertThat(projectedDave.getFirstname(), is("Dave"));
		assertThat(((TargetAware) projectedDave).getTarget(), is(instanceOf(Map.class)));
	}

	@Test
	public void projectsDtoUsingConstructorExpression() {

		Collection<CustomerDto> result = customers.findDtoWithConstructorExpression("Dave");

		assertThat(result, hasSize(1));
		assertThat(result.iterator().next().getFirstname(), is("Dave"));
	}

	@Test
	public void supportsProjectionInCombinationWithPagination() {

		Page<CustomerProjection> page = customers
				.findPagedProjectedBy(new PageRequest(0, 1, new Sort(Direction.ASC, "lastname")));

		assertThat(page.getContent().get(0).getFirstname(), is("Carter"));
	}

	@Test
	public void appliesProjectionToOptional() {
		assertThat(customers.findOptionalProjectionByLastname("Beauford").isPresent(), is(true));
	}
}
