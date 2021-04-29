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

import static org.assertj.core.api.Assertions.*;

import example.springdata.jpa.showcase.AbstractShowcaseTest;
import example.springdata.jpa.showcase.core.Account;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Integration tests for Spring Data JPA {@link AccountRepository}.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
public class AccountRepositoryIntegrationTest extends AbstractShowcaseTest {

	@Autowired AccountRepository accountRepository;
	@Autowired CustomerRepository customerRepository;

	@Test
	public void savesAccount() {
		var account = accountRepository.save(new Account());

		assertThat(account.getId()).isNotNull();
	}

	@Test
	public void findsCustomersAccounts() {

		var customer = customerRepository.findById(1L);
		var accounts = customer.map(accountRepository::findByCustomer).orElse(Collections.emptyList());

		assertThat(accounts).isNotEmpty();
		assertThat(customer).hasValueSatisfying(it -> assertThat(accounts.get(0).getCustomer()).isEqualTo(it));
	}
}
