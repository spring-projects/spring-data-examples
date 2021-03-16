/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.jdbc.jmolecules;

import static org.assertj.core.api.Assertions.*;

import example.springdata.jdbc.jmolecules.customer.Address;
import example.springdata.jdbc.jmolecules.customer.Customer;
import example.springdata.jdbc.jmolecules.customer.Customers;
import example.springdata.jdbc.jmolecules.order.Order;
import example.springdata.jdbc.jmolecules.order.Orders;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;

/**
 * @author Oliver Drotbohm
 */
@SpringBootTest
@RequiredArgsConstructor
class ApplicationIntegrationTests {

	private final ConfigurableApplicationContext context;

	@Test
	void exposesAssociationInMetamodel() {

		JdbcMappingContext mapping = context.getBean(JdbcMappingContext.class);
		RelationalPersistentEntity<?> entity = mapping.getRequiredPersistentEntity(Order.class);
		RelationalPersistentProperty customer = entity.getRequiredPersistentProperty("customer");

		assertThat(customer.isAssociation()).isTrue();
	}

	@Test
	void persistsDomainModel() {

		Address address = new Address("41 Greystreet", "Dreaming Tree", "2731");

		Customers customers = context.getBean(Customers.class);
		Customer customer = customers.save(new Customer("Dave", "Matthews", address));

		customer.setFirstname("Carter");
		customer = customers.save(customer);

		Orders orders = context.getBean(Orders.class);

		Order order = new Order(customer)
				.addLineItem("Foo")
				.addLineItem("Bar");

		Order result = orders.save(order);

		assertThat(customers.resolveRequired(result.getCustomer()));
	}
}
