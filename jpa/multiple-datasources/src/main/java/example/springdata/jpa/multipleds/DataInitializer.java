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
package example.springdata.jpa.multipleds;

import example.springdata.jpa.multipleds.customer.Customer;
import example.springdata.jpa.multipleds.customer.Customer.CustomerId;
import example.springdata.jpa.multipleds.customer.CustomerRepository;
import example.springdata.jpa.multipleds.order.Order;
import example.springdata.jpa.multipleds.order.Order.LineItem;
import example.springdata.jpa.multipleds.order.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Sample component to demonstrate how to work with repositories backed by different {@link DataSource}s. Note how we
 * explicitly select a transaction manager by name. In this particular case (only one operation on the repository) this
 * is not strictly necessary. However, if multiple repositories or multiple interactions on the very same repository are
 * to be executed in a method we need to expand the transaction boundary around these interactions. It's recommended to
 * create a dedicated annotation meta-annotated with {@code @Transactional("…")} to be able to refer to a particular
 * data source without using String qualifiers.
 * <p>
 * Also, not that one cannot interact with both databases in a single, transactional method as transactions are thread
 * bound in Spring an thus only a single transaction can be active in a single thread. See {@link Application#init()}
 * for how to orchestrate the calls.
 *
 * @author Oliver Gierke
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {

	private final @NonNull OrderRepository orders;
	private final @NonNull CustomerRepository customers;

	/**
	 * Initializes a {@link Customer}.
	 *
	 * @return
	 */
	@Transactional("customerTransactionManager")
	public CustomerId initializeCustomer() {
		return customers.save(new Customer("Dave", "Matthews")).getId();
	}

	/**
	 * Initializes an {@link Order}.
	 *
	 * @param customer must not be {@literal null}.
	 * @return
	 */
	@Transactional("orderTransactionManager")
	public Order initializeOrder(CustomerId customer) {

		Assert.notNull(customer, "Customer identifier must not be null!");

		var order = new Order(customer);
		order.add(new LineItem("Lakewood Guitar"));

		return orders.save(order);
	}
}
