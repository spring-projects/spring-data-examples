/*
 * Copyright 2017-2021 the original author or authors.
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository interface to manage {@link Airline} instances.
 *
 * @author Mark Paluch
 * @author Denis Rosa
 */
public interface ReactiveAirlineRepository extends ReactiveCrudRepository<Airline, String> {

	/**
	 * Derived query selecting by {@code iata}.
	 *
	 * @param code
	 * @return
	 */
	Mono<Airline> findByIata(String code);

	/**
	 * Query method using {@code airlines/all} view.
	 *
	 * @return
	 */
	@View(designDocument = "airlines", viewName = "all")
	Flux<Airline> findAllBy();
}
