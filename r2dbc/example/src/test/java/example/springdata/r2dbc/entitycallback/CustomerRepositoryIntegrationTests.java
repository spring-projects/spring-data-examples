/*
 * Copyright 2020-2021 the original author or authors.
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

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;

/**
 * Integration test using {@link org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback} to auto-generate an
 * Id from a sequence.
 *
 * @author Mark Paluch
 * @see ApplicationConfiguration#idGeneratingCallback(DatabaseClient)
 */
@SpringBootTest
class CustomerRepositoryIntegrationTests {

	@Autowired CustomerRepository customers;
	@Autowired DatabaseClient database;

	@Test
	void generatesIdOnInsert() throws IOException {

		var dave = new Customer(null, "Dave", "Matthews");

		this.customers.save(dave) //
				.as(StepVerifier::create) //
				.assertNext(actual -> {

					assertThat(dave.id()).isNull(); // immutable before save
					assertThat(actual.id()).isNotNull(); // after save
				}).verifyComplete();
	}
}
