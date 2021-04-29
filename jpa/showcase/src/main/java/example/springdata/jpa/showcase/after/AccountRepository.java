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
package example.springdata.jpa.showcase.after;

import example.springdata.jpa.showcase.core.Account;
import example.springdata.jpa.showcase.core.Customer;

import java.util.List;

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
