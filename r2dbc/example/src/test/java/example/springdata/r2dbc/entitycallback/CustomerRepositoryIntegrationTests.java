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
package example.springdata.r2dbc.entitycallback;

import static org.assertj.core.api.Assertions.*;

import reactor.test.StepVerifier;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test using {@link org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback} to auto-generate an
 * Id from a sequence.
 *
 * @author Mark Paluch
 * @see ApplicationConfiguration#idGeneratingCallback(DatabaseClient)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryIntegrationTests {

	@Autowired CustomerRepository customers;
	@Autowired DatabaseClient database;

	@Test
	public void generatesIdOnInsert() throws IOException {

		Customer dave = new Customer(null, "Dave", "Matthews");

		this.customers.save(dave) //
				.as(StepVerifier::create) //
				.assertNext(actual -> {

					assertThat(dave.getId()).isNull(); // immutable before save
					assertThat(actual.getId()).isNotNull(); // after save
				}).verifyComplete();
	}
}
