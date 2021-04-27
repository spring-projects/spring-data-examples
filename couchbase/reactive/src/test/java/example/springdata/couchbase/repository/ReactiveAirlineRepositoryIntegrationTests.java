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

import static org.assertj.core.api.Assertions.*;

import example.springdata.couchbase.model.Airline;
import example.springdata.couchbase.util.CouchbaseAvailableRule;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.couchbase.core.CouchbaseOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests showing basic CRUD operations through {@link ReactiveAirlineRepository}
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveAirlineRepositoryIntegrationTests {

	@ClassRule //
	public static CouchbaseAvailableRule COUCHBASE = CouchbaseAvailableRule.onLocalhost();

	@Autowired ReactiveAirlineRepository airlineRepository;

	@Autowired CouchbaseOperations couchbaseOperations;

	@Before
	public void before() {
		if (couchbaseOperations.existsById().one("LH")) {
			couchbaseOperations.removeById().one("LH");
		}
	}

	/**
	 * The derived query executes a N1QL query emitting a single element.
	 */
	@Test
	public void shouldFindAirlineN1ql() {

		airlineRepository.findByIata("TQ") //
				.as(StepVerifier::create) //
				.assertNext(it -> {
					assertThat(it.getCallsign()).isEqualTo("TXW");
				}).verifyComplete();
	}

	/**
	 * The derived query executes a N1QL query and the emitted element is used to invoke
	 * {@link org.springframework.data.repository.reactive.ReactiveCrudRepository#findById(Object)} for an Id-based
	 * lookup. Queries without a result do not emit a value.
	 */
	@Test
	public void shouldFindById() {

		Mono<Airline> airline = airlineRepository.findByIata("TQ") //
				.map(Airline::getId) //
				.flatMap(airlineRepository::findById);

		airline.as(StepVerifier::create) //
				.assertNext(it -> {

					assertThat(it.getCallsign()).isEqualTo("TXW");
				}).verifyComplete();

	}

	/**
	 * Find all {@link Airline}s applying the {@code airlines/all} view.
	 */
	@Test
	public void shouldFindAll() {
		airlineRepository.findAllBy().count() //
				.as(StepVerifier::create) //
				.assertNext(count -> {

					assertThat(count).isGreaterThan(100);
				}).verifyComplete();
	}

	/**
	 * Created elements are emitted by the
	 * {@link org.springframework.data.repository.reactive.ReactiveCrudRepository#save(Object)} method.
	 */
	@Test
	public void shouldCreateAirline() {

		Airline airline = new Airline();

		airline.setId("LH");
		airline.setIata("LH");
		airline.setIcao("DLH");
		airline.setCallsign("Lufthansa");
		airline.setName("Lufthansa");
		airline.setCountry("Germany");

		Mono<Airline> airlineMono = airlineRepository.save(airline) //
				.map(Airline::getId) //
				.flatMap(airlineRepository::findById);

		airlineMono.as(StepVerifier::create) //
				.expectNext(airline) //
				.verifyComplete();
	}
}
