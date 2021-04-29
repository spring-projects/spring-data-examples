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
package example.springdata.jpa.showcase.snippets;

import example.springdata.jpa.showcase.core.Account;
import example.springdata.jpa.showcase.core.Customer;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

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
	public static Specification<Customer> accountExpiresBefore(LocalDate date) {

		return (Specification<Customer>) (root, query, cb) -> {

			var accounts = query.from(Account.class);
			var expiryDate = accounts.<Date> get("expiryDate");
			var customerIsAccountOwner = cb.equal(accounts.<Customer> get("customer"), root);
			var accountExpiryDateBefore = cb.lessThan(expiryDate, java.sql.Date.valueOf(date));

			return cb.and(customerIsAccountOwner, accountExpiryDateBefore);
		};
	}
}
