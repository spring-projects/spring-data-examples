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

import static example.springdata.jpa.showcase.snippets.AccountPredicates.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.joda.time.LocalDate;

import example.springdata.jpa.showcase.core.Account;
import example.springdata.jpa.showcase.snippets.AccountRepository;

/**
 * @author Oliver Gierke
 */
public abstract class AccountRepositoryIntegrationTest {

	private AccountRepository accountRepository;

	public void removesExpiredAccountsCorrectly() throws Exception {

		accountRepository.removedExpiredAccounts(new LocalDate(2011, 1, 1));
		assertThat(accountRepository.count(), is(1L));
	}

	public void findsExpiredAccounts() {

		Account expired = accountRepository.findOne(1L);
		Account valid = accountRepository.findOne(2L);

		Iterable<Account> findAll = accountRepository.findAll(expiresBefore(new LocalDate(2011, 3, 1)));

		assertThat(findAll, hasItem(expired));
		assertThat(findAll, not(hasItem(valid)));
	}
}
