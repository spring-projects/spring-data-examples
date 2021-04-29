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
package example.springdata.jpa.showcase.snippets.test;

import static example.springdata.jpa.showcase.snippets.CustomerSpecifications.*;
import static org.assertj.core.api.Assertions.*;

import example.springdata.jpa.showcase.after.CustomerRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

/**
 * Snippets to show the usage of {@link Specification}s.
 *
 * @author Oliver Gierke
 */
public class CustomerRepositoryIntegrationTest {

	private CustomerRepository repository;

	public void findsCustomersBySpecification() throws Exception {

		var dave = repository.findById(1L);

		var expiryLimit = LocalDate.of(2011, 3, 1);
		var result = repository.findAll(accountExpiresBefore(expiryLimit));

		assertThat(result).hasSize(1);
		assertThat(dave).hasValueSatisfying(it -> assertThat(result).contains(it));
	}
}
