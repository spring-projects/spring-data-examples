/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.cassandra.streamoptional;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test to show the usage of JSR-310 date/time types with Spring Data Cassandra.
 *
 * @author Mark Paluch
 */
@CassandraKeyspace
@SpringBootTest(classes = CassandraConfiguration.class)
class Jsr310IntegrationTests {

	@Autowired OrderRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		repository.deleteAll();
	}

	@Test
	void findOneByJsr310Types() {

		var order = new Order("42", LocalDate.now(), ZoneId.systemDefault());

		repository.save(order);

		assertThat(repository.findOrderByOrderDateAndZoneId(order.getOrderDate(), order.getZoneId())).isEqualTo(order);
	}
}
