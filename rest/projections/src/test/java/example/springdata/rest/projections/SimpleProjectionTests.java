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
package example.springdata.rest.projections;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

/**
 * Test cases showing the programatic use of a {@link ProjectionFactory}.
 *
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
class SimpleProjectionTests {

	private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

	@Test
	void createMapBackedProjection() {

		var customer = factory.createProjection(Customer.class);
		customer.setFirstname("Dave");
		customer.setLastname("Matthews");

		// Verify accessors work
		assertThat(customer.getFirstname()).isEqualTo("Dave");
		assertThat(customer.getLastname()).isEqualTo("Matthews");

		// Verify evaluating a SpEL expression
		assertThat(customer.getFullName()).isEqualTo("Dave Matthews");
	}

	@Test
	void createsProxyForSourceMap() {

		Map<String, Object> backingMap = new HashMap<>();
		backingMap.put("firstname", "Dave");
		backingMap.put("lastname", "Matthews");

		var customer = factory.createProjection(Customer.class, backingMap);

		// Verify accessors work
		assertThat(customer.getFirstname()).isEqualTo("Dave");
		assertThat(customer.getLastname()).isEqualTo("Matthews");
	}

	interface Customer {

		String getFirstname();

		void setFirstname(String firstname);

		String getLastname();

		void setLastname(String lastname);

		@Value("#{target.firstname + ' ' + target.lastname}")
		String getFullName();
	}
}
