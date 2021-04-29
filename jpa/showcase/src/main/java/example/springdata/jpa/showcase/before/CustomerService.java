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
package example.springdata.jpa.showcase.before;

import example.springdata.jpa.showcase.core.Customer;

import java.util.List;

/**
 * Service interface for {@link Customer}s.
 *
 * @author Oliver Gierke
 */
public interface CustomerService {

	/**
	 * Returns the {@link Customer} with the given id or {@literal null} if no {@link Customer} with the given id was
	 * found.
	 *
	 * @param id
	 * @return
	 */
	Customer findById(Long id);

	/**
	 * Saves the given {@link Customer}.
	 *
	 * @param customer
	 * @return
	 */
	Customer save(Customer customer);

	/**
	 * Returns all customers.
	 *
	 * @return
	 */
	List<Customer> findAll();

	/**
	 * Returns the page of {@link Customer}s with the given index of the given size.
	 *
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<Customer> findAll(int page, int pageSize);

	/**
	 * Returns the page of {@link Customer}s with the given lastname and the given page index and page size.
	 *
	 * @param lastname
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<Customer> findByLastname(String lastname, int page, int pageSize);
}
