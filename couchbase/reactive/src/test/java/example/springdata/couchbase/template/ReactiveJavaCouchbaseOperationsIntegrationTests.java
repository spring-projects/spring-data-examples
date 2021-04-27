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
package example.springdata.couchbase.template;

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
import org.springframework.data.couchbase.core.ReactiveCouchbaseOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests showing basic CRUD operations through
 * {@link org.springframework.data.couchbase.core.ReactiveCouchbaseOperations}.
 *
 * @author Mark Paluch
 * @author Denis Rosa
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveJavaCouchbaseOperationsIntegrationTests {

	@ClassRule //
	public static CouchbaseAvailableRule COUCHBASE = CouchbaseAvailableRule.onLocalhost();

	@Autowired
	ReactiveCouchbaseOperations operations;

	@Autowired
	CouchbaseOperations couchbaseOperations;

	@Before
	public void before() {
		if (couchbaseOperations.existsById().one("LH")) {
			couchbaseOperations.removeById().one("LH");
		}
	}

	/**
	 * Find all {@link Airline}s applying the _class filter .
	 */
	@Test
	public void shouldFindByAll() {
		operations.findByQuery(Airline.class).all() //
				.count() //
				.as(StepVerifier::create) //
				.assertNext(count -> {

					assertThat(count).isGreaterThan(100);
				}) //
				.verifyComplete();
	}

	/**
	 * Created elements are emitted by {@link ReactiveCouchbaseOperations#upsertById(Class)} )}.
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

		Mono<Airline> airlineMono = operations.upsertById(Airline.class)
				.one(airline) //
				.map(Airline::getId) //
				.flatMap(id -> operations.findById(Airline.class).one(id));

		airlineMono.as(StepVerifier::create) //
				.expectNext(airline).verifyComplete();
	}
}
