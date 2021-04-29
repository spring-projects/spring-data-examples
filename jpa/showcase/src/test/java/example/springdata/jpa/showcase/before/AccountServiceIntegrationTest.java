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
package example.springdata.jpa.showcase.before;

import static org.assertj.core.api.Assertions.*;

import example.springdata.jpa.showcase.AbstractShowcaseTest;
import example.springdata.jpa.showcase.core.Account;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Integration test for {@link AccountService}.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 * @author Jens Schauder
 */
class AccountServiceIntegrationTest extends AbstractShowcaseTest {

	@Autowired AccountService accountService;
	@Autowired CustomerService customerService;

	@Test
	void savesAccount() {

		var account = accountService.save(new Account());
		assertThat(account.getId()).isNotNull();
	}

	@Test
	void testname() throws Exception {

		var customer = customerService.findById(1L);

		var accounts = accountService.findByCustomer(customer);

		assertThat(accounts).isNotEmpty();
		assertThat(accounts.get(0).getCustomer()).isEqualTo(customer);
	}
}
