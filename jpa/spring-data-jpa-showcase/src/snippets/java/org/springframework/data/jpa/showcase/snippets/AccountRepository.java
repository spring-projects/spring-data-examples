package org.springframework.data.jpa.showcase.snippets;

import java.util.List;

import org.springframework.data.jpa.showcase.core.Account;
import org.springframework.data.jpa.showcase.core.Customer;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repository to manage {@link Account} instances.
 * 
 * @author Oliver Gierke
 */
@NoRepositoryBean
public interface AccountRepository extends CrudRepository<Account, Long>, AccountRepositoryCustom,
		QueryDslPredicateExecutor<Account> {

	/**
	 * Returns all accounts belonging to the given {@link Customer}.
	 * 
	 * @param customer
	 * @return
	 */
	List<Account> findByCustomer(Customer customer);
}
