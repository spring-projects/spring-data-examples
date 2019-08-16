/*
 * Copyright 2014-2019 the original author or authors.
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

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for {@link OrderRepository}.
 *
 * @author Thomas Darimont
 * @author Oliver Gierke
 * @author Christoph Strobl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryIntegrationTests {

	@Autowired OrderRepository repository;

	private final static LineItem product1 = new LineItem("p1", 1.23);
	private final static LineItem product2 = new LineItem("p2", 0.87, 2);
	private final static LineItem product3 = new LineItem("p3", 5.33);

	@Before
	public void setup() {
		repository.deleteAll();
	}

	@Test
	public void createsInvoiceViaAggregation() {

		Order order = new Order("c42", new Date()).//
				addItem(product1).addItem(product2).addItem(product3);
		order = repository.save(order);

		Invoice invoice = repository.getInvoiceFor(order);

		assertThat(invoice).isNotNull();
		assertThat(invoice.getOrderId()).isEqualTo(order.getId());
		assertThat(invoice.getNetAmount()).isCloseTo(8.3D, offset(0.00001));
		assertThat(invoice.getTaxAmount()).isCloseTo(1.577D, offset(0.00001));
		assertThat(invoice.getTotalAmount()).isCloseTo(9.877, offset(0.00001));
	}

	@Test
	public void declarativeAggregationWithSort() {

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
	public void multiStageDeclarativeAggregation() {

		repository.save(new Order("c42", new Date()).addItem(product1));
		repository.save(new Order("c42", new Date()).addItem(product2));
		repository.save(new Order("c42", new Date()).addItem(product3));

		repository.save(new Order("b12", new Date()).addItem(product1));
		repository.save(new Order("b12", new Date()).addItem(product1));

		assertThat(repository.totalOrdersForCustomer("c42")).isEqualTo(3);
	}
}
