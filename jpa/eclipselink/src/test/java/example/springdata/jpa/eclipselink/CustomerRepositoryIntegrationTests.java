/*
 * Copyright 2015-2021 the original author or authors.
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
package example.springdata.jpa.eclipselink;

import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test for {@link CustomerRepository}.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */

@SpringBootTest
@Transactional
public class CustomerRepositoryIntegrationTests {

	@Autowired CustomerRepository customers;

	@Test
	public void createsCustomer() {

		Customer dave = customers.save(new Customer("Dave", "Matthews"));

		assertThat(customers.findById(dave.getId())).hasValue(dave);
	}
}
