/*
 * Copyright 2013-2015 the original author or authors.
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
package example.springdata.jpa.java8;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

/**
 * Repository to manage {@link Customer} instances.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public interface CustomerRepository extends Repository<Customer, Long> {

	/**
	 * Special customization of {@link CrudRepository#findOne(java.io.Serializable)} to return a JDK 8 {@link Optional}.
	 * 
	 * @param id
	 * @return
	 */
	Optional<Customer> findOne(Long id);

	/**
	 * Saves the given {@link Customer}.
	 * 
	 * @param customer
	 * @return
	 */
	<S extends Customer> S save(S customer);

	/**
	 * Sample method to derive a query from using JDK 8's {@link Optional} as return type.
	 * 
	 * @param lastname
	 * @return
	 */
	Optional<Customer> findByLastname(String lastname);

	/**
	 * Sample default method to show JDK 8 feature support.
	 * 
	 * @param customer
	 * @return
	 */
	default Optional<Customer> findByLastname(Customer customer) {
		return findByLastname(customer == null ? null : customer.lastname);
	}

	/**
	 * Sample method to demonstrate support for {@link Stream} as a return type with a custom query. The query is executed
	 * in a streaming fashion which means that the method returns as soon as the first results are ready.
	 * 
	 * @return
	 */
	@Query("select c from Customer c")
	Stream<Customer> streamAllCustomers();

	/**
	 * Sample method to demonstrate support for {@link Stream} as a return type with a derived query. The query is
	 * executed in a streaming fashion which means that the method returns as soon as the first results are ready.
	 * 
	 * @return
	 */
	Stream<Customer> findAllByLastnameIsNotNull();

	@Async
	CompletableFuture<List<Customer>> readAllBy();
}
