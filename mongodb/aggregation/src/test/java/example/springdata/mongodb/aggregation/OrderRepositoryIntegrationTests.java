/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.mongodb.aggregation;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for {@link OrderRepository}.
 * 
 * @author Thomas Darimont
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
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

		assertThat(invoice, is(notNullValue()));
		assertThat(invoice.getOrderId(), is(order.getId()));
		assertThat(invoice.getNetAmount(), is(closeTo(8.3, 000001)));
		assertThat(invoice.getTaxAmount(), is(closeTo(1.577, 000001)));
		assertThat(invoice.getTotalAmount(), is(closeTo(9.877, 000001)));
	}
}
