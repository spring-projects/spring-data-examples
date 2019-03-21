/*
 * Copyright 2016-2018 the original author or authors.
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
package example.springdata.cassandra.java8;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Version;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test to show the usage of JSR-310 date/time types with Spring Data Cassandra.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CassandraConfiguration.class)
public class Jsr310IntegrationTests {

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost()
			.atLeast(Version.parse("3.0"));

	@Autowired OrderRepository repository;

	@Before
	public void setUp() throws Exception {
		repository.deleteAll();
	}

	@Test
	public void findOneByJsr310Types() {

		Order order = new Order("42", LocalDate.now(), ZoneId.systemDefault());

		repository.save(order);

		assertThat(repository.findOrderByOrderDateAndZoneId(order.getOrderDate(), order.getZoneId())).isEqualTo(order);
	}

	@Test
	public void findOneByConvertedTypes() {

		Order order = new Order("42", LocalDate.of(2010, 1, 2), ZoneId.systemDefault());

		repository.save(order);

		com.datastax.driver.core.LocalDate date = com.datastax.driver.core.LocalDate.fromYearMonthDay(2010, 1, 2);
		String zoneId = order.getZoneId().getId();

		assertThat(repository.findOrderByDate(date, zoneId)).isEqualTo(order);
	}
}
