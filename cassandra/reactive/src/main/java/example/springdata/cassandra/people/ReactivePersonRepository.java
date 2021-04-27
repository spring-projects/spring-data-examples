/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.cassandra.people;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository interface to manage {@link Person} instances.
 *
 * @author Mark Paluch
 */
public interface ReactivePersonRepository extends ReactiveCrudRepository<Person, String> {

	/**
	 * Derived query selecting by {@code lastname}.
	 *
	 * @param lastname
	 * @return
	 */
	Flux<Person> findByLastname(String lastname);

	/**
	 * String query selecting one entity.
	 *
	 * @param lastname
	 * @return
	 */
	@Query("SELECT * FROM person WHERE firstname = ?0 and lastname  = ?1")
	Mono<Person> findByFirstnameInAndLastname(String firstname, String lastname);

	/**
	 * Derived query selecting by {@code lastname}. {@code lastname} uses deferred resolution that does not require
	 * blocking to obtain the parameter value.
	 *
	 * @param lastname
	 * @return
	 */
	Flux<Person> findByLastname(Mono<String> lastname);

	/**
	 * Derived query selecting by {@code firstname} and {@code lastname}. {@code firstname} uses deferred resolution that
	 * does not require blocking to obtain the parameter value.
	 *
	 * @param firstname
	 * @param lastname
	 * @return
	 */
	Mono<Person> findByFirstnameAndLastname(Mono<String> firstname, String lastname);
}
