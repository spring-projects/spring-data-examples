package org.springframework.data.jpa.showcase.after;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.showcase.core.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository to manage {@link Customer} instances.
 * 
 * @author Oliver Gierke
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

	/**
	 * Returns a page of {@link Customer}s with the given lastname.
	 * 
	 * @param lastname
	 * @param pageable
	 * @return
	 */
	Page<Customer> findByLastname(String lastname, Pageable pageable);
}
