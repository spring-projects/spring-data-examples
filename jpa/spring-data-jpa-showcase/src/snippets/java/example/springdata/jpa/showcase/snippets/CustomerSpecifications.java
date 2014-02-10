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
package example.springdata.jpa.showcase.snippets;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

import example.springdata.jpa.showcase.core.Account;
import example.springdata.jpa.showcase.core.Customer;

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
