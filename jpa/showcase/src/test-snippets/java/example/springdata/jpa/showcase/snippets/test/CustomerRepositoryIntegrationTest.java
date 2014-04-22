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
package example.springdata.jpa.showcase.snippets.test;

import static example.springdata.jpa.showcase.snippets.CustomerSpecifications.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.jpa.domain.Specifications.*;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

import example.springdata.jpa.showcase.after.CustomerRepository;
import example.springdata.jpa.showcase.core.Customer;

/**
 * Snippets to show the usage of {@link Specification}s.
 * 
 * @author Oliver Gierke
 */
public class CustomerRepositoryIntegrationTest {

	private CustomerRepository repository;

	public void findsCustomersBySpecification() throws Exception {

		Customer dave = repository.findOne(1L);

		LocalDate expiryLimit = new LocalDate(2011, 3, 1);
		List<Customer> result = repository.findAll(where(accountExpiresBefore(expiryLimit)));

		assertThat(result.size(), is(1));
		assertThat(result, hasItems(dave));
	}
}
