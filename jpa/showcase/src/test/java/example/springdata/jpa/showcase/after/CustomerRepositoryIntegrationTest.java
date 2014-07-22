/*
 * Copyright 2011-2014 the original author or authors.
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
package example.springdata.jpa.showcase.after;

import static example.springdata.jpa.showcase.snippets.CustomerSpecifications.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.jpa.domain.Specifications.*;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import example.springdata.jpa.showcase.AbstractShowcaseTest;
import example.springdata.jpa.showcase.core.Customer;

/**
 * Integration tests for Spring Data JPA {@link CustomerRepository}.
 * 
 * @author Oliver Gierke
 */
public class CustomerRepositoryIntegrationTest extends AbstractShowcaseTest {

	@Autowired CustomerRepository repository;

	@Test
	public void findsAllCustomers() throws Exception {

		Iterable<Customer> result = repository.findAll();

		assertThat(result, is(notNullValue()));
		assertTrue(result.iterator().hasNext());
	}

	@Test
	public void findsFirstPageOfMatthews() throws Exception {

		Page<Customer> customers = repository.findByLastname("Matthews", new PageRequest(0, 2));

		assertThat(customers.getContent().size(), is(2));
		assertFalse(customers.hasPrevious());
	}

	@Test
	public void findsCustomerById() throws Exception {

		Customer customer = repository.findOne(2L);

		assertThat(customer.getFirstname(), is("Carter"));
		assertThat(customer.getLastname(), is("Beauford"));
	}

	@Test
	public void findsCustomersBySpecification() throws Exception {

		Customer dave = repository.findOne(1L);

		LocalDate expiryLimit = new LocalDate(2011, 3, 1);
		List<Customer> result = repository.findAll(where(accountExpiresBefore(expiryLimit)));

		assertThat(result.size(), is(1));
		assertThat(result, hasItems(dave));
	}
}
