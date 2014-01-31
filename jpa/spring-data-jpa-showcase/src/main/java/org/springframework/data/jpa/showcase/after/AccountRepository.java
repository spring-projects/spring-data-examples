package org.springframework.data.jpa.showcase.after;

import java.util.List;

import org.springframework.data.jpa.showcase.core.Account;
import org.springframework.data.jpa.showcase.core.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository to manage {@link Account} instances.
 * 
 * @author Oliver Gierke
 */
public interface AccountRepository extends CrudRepository<Account, Long> {

	/**
	 * Returns all accounts belonging to the given {@link Customer}.
	 * 
	 * @param customer
	 * @return
	 */
	List<Account> findByCustomer(Customer customer);
}
