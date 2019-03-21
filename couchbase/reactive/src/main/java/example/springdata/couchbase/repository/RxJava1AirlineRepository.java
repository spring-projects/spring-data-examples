/*
 * Copyright 2017-2018 the original author or authors.
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
package example.springdata.couchbase.repository;

import example.springdata.couchbase.model.Airline;
import rx.Observable;
import rx.Single;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.repository.Repository;

/**
 * Repository interface to manage {@link Airline} instances.
 *
 * @author Mark Paluch
 */
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "airlines")
public interface RxJava1AirlineRepository extends Repository<Airline, String> {

	/**
	 * Derived query selecting by {@code iataCode}.
	 *
	 * @param code
	 * @return
	 */
	Single<Airline> findAirlineByIataCode(String code);

	/**
	 * Query method using {@code airlines/all} view.
	 *
	 * @return
	 */
	@View(designDocument = "airlines", viewName = "all")
	Observable<Airline> findAllBy();

	/**
	 * Overloaded {@link org.springframework.data.repository.reactive.ReactiveCrudRepository#findById(Object)} method
	 * returning a RxJava 1 {@link Single}.
	 *
	 * @param id
	 * @return
	 */
	Single<Airline> findById(String id);

	/**
	 * Overloaded {@link org.springframework.data.repository.reactive.ReactiveCrudRepository#save(Object)} method
	 * returning a RxJava 1 {@link Single}.
	 *
	 * @param airline
	 * @return
	 */
	Single<Airline> save(Airline airline);
}
