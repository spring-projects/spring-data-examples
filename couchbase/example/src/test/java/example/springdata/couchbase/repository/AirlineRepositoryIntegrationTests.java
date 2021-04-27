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

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.couchbase.core.CouchbaseOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests showing basic CRUD operations through {@link AirlineRepository}.
 *
 * @author Denis Rosa
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AirlineRepositoryIntegrationTests {

	@ClassRule //
	public static CouchbaseAvailableRule COUCHBASE = CouchbaseAvailableRule.onLocalhost();

	@Autowired AirlineRepository airlineRepository;

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

		List<Airline> airlines = airlineRepository.findByIata("TQ");
		assertThat(airlines.get(0).getCallsign()).isEqualTo("TXW");
	}

	/**
	 * The derived query executes a N1QL query and the emitted element is used to invoke
	 * {@link org.springframework.data.repository.reactive.ReactiveCrudRepository#findById(Object)} for an Id-based
	 * lookup. Queries without a result do not emit a value.
	 */
	@Test
	public void shouldFindById() {

		Airline airline = airlineRepository.findByIata("TQ").get(0);
		assertThat(airlineRepository.findById(airline.getId()).isPresent());
	}

	/**
	 * Find all {@link Airline}s applying the {@code airlines/all} view.
	 */
	@Test
	public void shouldFindByView() {

		List<Airline> airlines = airlineRepository.findAllBy();

		assertThat(airlines).hasSizeGreaterThan(100);
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

		airlineRepository.save(airline);

		assertThat(airlineRepository.findById("LH")).contains(airline);
	}
}
