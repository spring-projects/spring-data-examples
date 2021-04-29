/*
 * Copyright 2011-2021 the original author or authors.
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
package example.springdata.jpa.showcase.after;

import static example.springdata.jpa.showcase.snippets.CustomerSpecifications.*;
import static org.assertj.core.api.Assertions.*;

import example.springdata.jpa.showcase.AbstractShowcaseTest;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

/**
 * Integration tests for Spring Data JPA {@link CustomerRepository}.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
public class CustomerRepositoryIntegrationTest extends AbstractShowcaseTest {

	@Autowired CustomerRepository repository;

	@Test
	public void findsAllCustomers() throws Exception {
		assertThat(repository.findAll()).isNotEmpty();
	}

	@Test
	public void findsFirstPageOfMatthews() throws Exception {

		var customers = repository.findByLastname("Matthews", PageRequest.of(0, 2));

		assertThat(customers.getContent()).hasSize(2);
		assertThat(customers.hasPrevious()).isFalse();
	}

	@Test
	public void findsCustomerById() throws Exception {

		assertThat(repository.findById(2L)).hasValueSatisfying(it -> {
			assertThat(it.getFirstname()).isEqualTo("Carter");
			assertThat(it.getLastname()).isEqualTo("Beauford");
		});
	}

	@Test
	public void findsCustomersBySpecification() throws Exception {

		var dave = repository.findById(1L);

		var expiryLimit = LocalDate.of(2011, 3, 1);
		var result = repository.findAll(accountExpiresBefore(expiryLimit));

		assertThat(result).hasSize(1);
		assertThat(dave).hasValueSatisfying(it -> assertThat(result).contains(it));
	}
}
