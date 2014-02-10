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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import example.springdata.jpa.showcase.AbstractShowcaseTest;
import example.springdata.jpa.showcase.core.Account;
import example.springdata.jpa.showcase.core.Customer;

/**
 * Integration tests for Spring Data JPA {@link AccountRepository}.
 * 
 * @author Oliver Gierke
 */
public class AccountRepositoryIntegrationTest extends AbstractShowcaseTest {

	@Autowired AccountRepository accountRepository;
	@Autowired CustomerRepository customerRepository;

	@Test
	public void savesAccount() {

		Account account = accountRepository.save(new Account());
		assertThat(account.getId(), is(notNullValue()));
	}

	@Test
	public void findsCustomersAccounts() {

		Customer customer = customerRepository.findOne(1L);
		List<Account> accounts = accountRepository.findByCustomer(customer);

		assertFalse(accounts.isEmpty());
		assertThat(accounts.get(0).getCustomer(), is(customer));
	}
}
