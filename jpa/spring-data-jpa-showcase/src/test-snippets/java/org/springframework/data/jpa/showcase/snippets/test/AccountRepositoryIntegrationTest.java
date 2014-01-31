package org.springframework.data.jpa.showcase.snippets.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.jpa.showcase.snippets.AccountPredicates.*;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.showcase.core.Account;
import org.springframework.data.jpa.showcase.snippets.AccountRepository;

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
