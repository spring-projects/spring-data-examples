package org.springframework.data.jpa.showcase.before;

import java.util.List;

import org.springframework.data.jpa.showcase.core.Account;
import org.springframework.data.jpa.showcase.core.Customer;

/**
 * Service interface for {@link Account}s.
 * 
 * @author Oliver Gierke
 */
public interface AccountService {

	/**
	 * Saves the given {@link Account}.
	 * 
	 * @param account
	 * @return
	 */
	Account save(Account account);

	/**
	 * Returns all {@link Account}s of the given {@link Customer}.
	 * 
	 * @param customer
	 * @return
	 */
	List<Account> findByCustomer(Customer customer);
}
