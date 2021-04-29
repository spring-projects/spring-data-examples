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
package example.springdata.jdbc.jmolecules.order;

import example.springdata.jdbc.jmolecules.customer.Customer;
import example.springdata.jdbc.jmolecules.customer.Customer.CustomerId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Oliver Drotbohm
 */
@Table("MY_ORDER")
@Getter
@ToString
@RequiredArgsConstructor
public class Order implements AggregateRoot<Order, Order.OrderId> {

	private final OrderId id;
	private @Column("FOO") List<LineItem> lineItems;
	private Association<Customer, CustomerId> customer;

	public Order(Customer customer) {

		this.id = new OrderId(UUID.randomUUID());
		this.customer = Association.forAggregate(customer);
		this.lineItems = new ArrayList<>();
	}

	public Order addLineItem(String description) {

		var item = new LineItem(description);

		this.lineItems.add(item);

		return this;
	}

	public record OrderId(UUID id) implements Identifier {}
}
