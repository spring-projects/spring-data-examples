/*
 * Copyright 2017-2018 the original author or authors.
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
package example.springdata.couchbase.template;

import static org.assertj.core.api.Assertions.*;

import example.springdata.couchbase.model.Airline;
import example.springdata.couchbase.util.CouchbaseAvailableRule;
import rx.Observable;
import rx.observers.AssertableSubscriber;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.couchbase.core.RxJavaCouchbaseOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.view.ViewQuery;

/**
 * Integration tests showing basic CRUD operations through
 * {@link org.springframework.data.couchbase.core.RxJavaCouchbaseOperations}.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RxJavaCouchbaseOperationsIntegrationTests {

	@ClassRule //
	public static CouchbaseAvailableRule COUCHBASE = CouchbaseAvailableRule.onLocalhost();

	@Autowired RxJavaCouchbaseOperations operations;

	@Before
	public void before() {
		operations.findById("LH", Airline.class).flatMap(operations::remove).test().awaitTerminalEvent();
	}

	/**
	 * The derived query executes a N1QL query emitting a single element.
	 */
	@Test
	public void shouldFindAirlineN1ql() {

		String n1ql = "SELECT META(`travel-sample`).id AS _ID, META(`travel-sample`).cas AS _CAS, `travel-sample`.* " + //
				"FROM `travel-sample` " + //
				"WHERE (`iata` = \"TQ\") AND `_class` = \"example.springdata.couchbase.model.Airline\"";

		AssertableSubscriber<Airline> subscriber = operations.findByN1QL(N1qlQuery.simple(n1ql), Airline.class) //
				.test() //
				.awaitTerminalEvent() //
				.assertCompleted();

		assertThat(subscriber.getOnNextEvents()).hasSize(1);
		assertThat(subscriber.getOnNextEvents().get(0).getCallsign()).isEqualTo("TXW");
	}

	/**
	 * Find all {@link Airline}s applying the {@code airlines/all} view.
	 */
	@Test
	public void shouldFindByView() {

		Observable<Airline> airlines = operations.findByView(ViewQuery.from("airlines", "all"), Airline.class);

		airlines.test().awaitTerminalEvent().assertValueCount(187);
	}

	/**
	 * Created elements are emitted by {@link RxJavaCouchbaseOperations#save(Object)}.
	 */
	@Test
	public void shouldCreateAirline() {

		Airline airline = new Airline();

		airline.setId("LH");
		airline.setIataCode("LH");
		airline.setIcao("DLH");
		airline.setCallsign("Lufthansa");
		airline.setName("Lufthansa");
		airline.setCountry("Germany");

		Observable<Airline> single = operations.save(airline) //
				.map(Airline::getId) //
				.flatMap(id -> operations.findById(id, Airline.class));

		single.test().awaitTerminalEvent().assertResult(airline);
	}
}
