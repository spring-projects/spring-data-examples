/*
 * Copyright 2020 the original author or authors.
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
package example.springdata.cassandra.auditing;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;
import reactor.test.StepVerifier;

import java.time.Instant;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests showing Reactive Auditing with Cassandra in action.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@DataCassandraTest
public class AuditingIntegrationTests {

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost();

	@Autowired OrderRepository orderRepository;

	@Test
	public void shouldUpdateAuditor() throws InterruptedException {

		Order order = new Order("4711");
		order.setNew(true);

		orderRepository.save(order).as(StepVerifier::create).assertNext(actual -> {

			assertThat(actual.getCreatedBy()).isEqualTo("the-current-user");
			assertThat(actual.getCreatedDate()).isBetween(Instant.now().minusSeconds(60), Instant.now().plusSeconds(60));
			assertThat(actual.getLastModifiedBy()).isEqualTo("the-current-user");
			assertThat(actual.getLastModifiedDate()).isBetween(Instant.now().minusSeconds(60), Instant.now().plusSeconds(60));

		}).verifyComplete();

		Thread.sleep(10);

		order = orderRepository.findById("4711").block();

		orderRepository.save(order).as(StepVerifier::create).assertNext(actual -> {

			assertThat(actual.getCreatedBy()).isEqualTo("the-current-user");
			assertThat(actual.getCreatedDate()).isBefore(actual.getLastModifiedDate());
			assertThat(actual.getLastModifiedBy()).isEqualTo("the-current-user");
			assertThat(actual.getLastModifiedDate()).isBetween(Instant.now().minusSeconds(60), Instant.now().plusSeconds(60));

		}).verifyComplete();
	}
}
