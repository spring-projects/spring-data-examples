/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.mongodb.aggregation;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Offset.offset;

import example.springdata.mongodb.util.MongoContainers;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration tests for {@link OrderRepository}.
 *
 * @author Thomas Darimont
 * @author Oliver Gierke
 * @author Christoph Strobl
 * @author Divya Srivastava
 */
@Testcontainers
@SpringBootTest
class OrderRepositoryIntegrationTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired OrderRepository repository;

	private final static LineItem product1 = new LineItem("p1", 1.23);
	private final static LineItem product2 = new LineItem("p2", 0.87, 2);
	private final static LineItem product3 = new LineItem("p3", 5.33);

	@BeforeEach
	void setup() {
		repository.deleteAll();
	}

	@Test
	void createsInvoiceViaProgrammaticAggregation() {

		var order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
		order = repository.save(order);

		var invoice = repository.getInvoiceFor(order);

		assertThat(invoice).isNotNull();
		assertThat(invoice.orderId()).isEqualTo(order.getId());
		assertThat(invoice.netAmount()).isCloseTo(8.3D, offset(0.00001));
		assertThat(invoice.taxAmount()).isCloseTo(1.577D, offset(0.00001));
		assertThat(invoice.totalAmount()).isCloseTo(9.877, offset(0.00001));
	}

	@Test
	void createsInvoiceViaDeclarativeAggregation() {

		var order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
		order = repository.save(order);

		var invoice = repository.aggregateInvoiceForOrder(order.getId());

		assertThat(invoice).isNotNull();
		assertThat(invoice.orderId()).isEqualTo(order.getId());
		assertThat(invoice.netAmount()).isCloseTo(8.3D, offset(0.00001));
		assertThat(invoice.taxAmount()).isCloseTo(1.577D, offset(0.00001));
		assertThat(invoice.totalAmount()).isCloseTo(9.877, offset(0.00001));
	}

	@Test
	void declarativeAggregationWithSort() {

		repository.save(new Order("c42", new Date()).addItem(product1));
		repository.save(new Order("c42", new Date()).addItem(product2));
		repository.save(new Order("c42", new Date()).addItem(product3));

		repository.save(new Order("b12", new Date()).addItem(product1));
		repository.save(new Order("b12", new Date()).addItem(product1));

		assertThat(repository.totalOrdersPerCustomer(Sort.by(Sort.Order.desc("total")))) //
				.containsExactly( //
						new OrdersPerCustomer("c42", 3L), new OrdersPerCustomer("b12", 2L) //
				);
	}

	@Test
	void multiStageDeclarativeAggregation() {

		repository.save(new Order("c42", new Date()).addItem(product1));
		repository.save(new Order("c42", new Date()).addItem(product2));
		repository.save(new Order("c42", new Date()).addItem(product3));

		repository.save(new Order("b12", new Date()).addItem(product1));
		repository.save(new Order("b12", new Date()).addItem(product1));

		assertThat(repository.totalOrdersForCustomer("c42")).isEqualTo(3);
	}

}
