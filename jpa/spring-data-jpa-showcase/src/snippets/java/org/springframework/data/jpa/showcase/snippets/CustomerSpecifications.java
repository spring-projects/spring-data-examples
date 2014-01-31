package org.springframework.data.jpa.showcase.snippets;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.showcase.core.Account;
import org.springframework.data.jpa.showcase.core.Customer;

/**
 * Collection of {@link Specification} implementations.
 * 
 * @author Oliver Gierke
 */
public class CustomerSpecifications {

	/**
	 * All customers with an {@link Account} expiring before the given date.
	 * 
	 * @param date
	 * @return
	 */
	public static Specification<Customer> accountExpiresBefore(final LocalDate date) {

		return new Specification<Customer>() {

			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Root<Account> accounts = query.from(Account.class);
				Path<Date> expiryDate = accounts.<Date> get("expiryDate");
				Predicate customerIsAccountOwner = cb.equal(accounts.<Customer> get("customer"), root);
				Predicate accountExpiryDateBefore = cb.lessThan(expiryDate, date.toDateTimeAtStartOfDay().toDate());

				return cb.and(customerIsAccountOwner, accountExpiryDateBefore);
			}
		};
	}
}
