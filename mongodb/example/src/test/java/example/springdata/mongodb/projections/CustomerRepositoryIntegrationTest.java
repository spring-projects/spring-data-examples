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
package example.springdata.mongodb.projections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collection;

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

/**
 * Integration tests for {@link CustomerRepository} to show projection capabilities.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerRepositoryIntegrationTest {

	@Configuration
	@EnableAutoConfiguration
	static class Config {}

	@Autowired CustomerRepository customers;

	Customer dave, carter;

	@Before
	public void setUp() {

		customers.deleteAll();

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

		assertThat(result, is(notNullValue()));
		assertThat(result.getFullName(), is("Dave Matthews"));

		// Proxy backed by original instance as the projection uses dynamic elements
		assertThat(((TargetAware) result).getTarget(), is(instanceOf(Customer.class)));
	}

	@Test
	public void projectIndividualInstance() {

		CustomerProjection result = customers.findProjectedById(dave.getId());

		assertThat(result, is(notNullValue()));
		assertThat(result.getFirstname(), is("Dave"));
		assertThat(((TargetAware) result).getTarget(), is(instanceOf(Customer.class)));
	}

	@Test
	public void supportsProjectionInCombinationWithPagination() {

		Page<CustomerProjection> page = customers
				.findPagedProjectedBy(PageRequest.of(0, 1, Sort.by(Direction.ASC, "lastname")));

		assertThat(page.getContent().get(0).getFirstname(), is("Carter"));
	}
}
