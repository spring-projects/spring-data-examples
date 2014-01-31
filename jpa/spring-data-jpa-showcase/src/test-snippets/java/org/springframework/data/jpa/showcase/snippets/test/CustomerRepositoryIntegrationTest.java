package org.springframework.data.jpa.showcase.snippets.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.jpa.domain.Specifications.*;
import static org.springframework.data.jpa.showcase.snippets.CustomerSpecifications.*;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.showcase.after.CustomerRepository;
import org.springframework.data.jpa.showcase.core.Customer;

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
