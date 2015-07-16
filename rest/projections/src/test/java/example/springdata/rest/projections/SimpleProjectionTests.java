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
package example.springdata.rest.projections;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

/**
 * Test cases showing the programatic use of a {@link ProjectionFactory}.
 * 
 * @author Oliver Gierke
 */
public class SimpleProjectionTests {

	ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

	@Test
	public void createMapBackedProjection() {

		Customer customer = factory.createProjection(Customer.class);
		customer.setFirstname("Dave");
		customer.setLastname("Matthews");

		// Verify accessors work
		assertThat(customer.getFirstname(), is("Dave"));
		assertThat(customer.getLastname(), is("Matthews"));

		// Verify evaluating a SpEL expression
		assertThat(customer.getFullName(), is("Dave Matthews"));
	}

	@Test
	public void createsProxyForSourceMap() {

		Map<String, Object> backingMap = new HashMap<>();
		backingMap.put("firstname", "Dave");
		backingMap.put("lastname", "Matthews");

		Customer customer = factory.createProjection(Customer.class, backingMap);

		// Verify accessors work
		assertThat(customer.getFirstname(), is("Dave"));
		assertThat(customer.getLastname(), is("Matthews"));
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
