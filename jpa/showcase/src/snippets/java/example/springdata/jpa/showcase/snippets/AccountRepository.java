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

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@NoRepositoryBean
public interface AccountRepository
		extends CrudRepository<Account, Long>, AccountRepositoryCustom, QuerydslPredicateExecutor<Account> {

	/**
	 * Returns all accounts belonging to the given {@link Customer}.
	 *
	 * @param customer
	 * @return
	 */
	List<Account> findByCustomer(Customer customer);
}
