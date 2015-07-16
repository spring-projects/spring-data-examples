/*
 * Copyright 2015 the original author or authors.
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
package example.springdata.rest.headers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import example.springdata.rest.headers.Application;
import example.springdata.rest.headers.Customer;
import example.springdata.rest.headers.CustomerRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests to bootstrap the application.
 * 
 * @author Oliver Gierke
 * @soundtrack The Intersphere - Out of phase (Live at Alte Feuerwache Mannheim)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationIntegrationTests {

	@Autowired CustomerRepository repository;

	@Test
	public void initializesRepositoryWithSampleData() {

		Iterable<Customer> result = repository.findAll();

		assertThat(result, is(iterableWithSize(1)));
		assertThat(result.iterator().next().getLastModifiedDate(), is(notNullValue()));
	}
}
