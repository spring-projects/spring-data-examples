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
package example.springdata.cassandra.spel;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.spel.ApplicationConfiguration.Tenant;
import example.springdata.cassandra.util.CassandraKeyspace;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

import java.util.Arrays;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests showing the SpEL context extension in action.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@DataCassandraTest
public class ExpressionIntegrationTests {

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost();

	@Autowired EmployeeRepository employeeRepository;

	@Before
	public void before() {

		employeeRepository.deleteAll().as(StepVerifier::create).verifyComplete();

		employeeRepository
				.saveAll(Arrays.asList(new Employee("breaking-bad", "Walter"), new Employee("breaking-bad", "Hank"),
						new Employee("south-park", "Hank"))) //
				.as(StepVerifier::create) //
				.expectNextCount(3) //
				.verifyComplete();
	}

	@Test
	public void shouldFindByTenantIdAndName() {

		employeeRepository.findAllByName("Walter") //
				.contextWrite(Context.of(Tenant.class, new Tenant("breaking-bad"))).as(StepVerifier::create) //
				.assertNext(actual -> {
					assertThat(actual.getTenantId()).isEqualTo("breaking-bad");
				}).verifyComplete();

	}
}
