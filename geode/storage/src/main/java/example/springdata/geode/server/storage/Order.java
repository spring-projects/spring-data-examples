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
package example.springdata.geode.server.storage;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

/**
 * Orders object used in the examples
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Data
@Region("Orders")
public class Order implements Serializable {

	@Id private Long id;
	private Long customerId;
	private Address billingAddress;
	private Address shippingAddress;
	private List<LineItem> lineItems = new ArrayList<>();

	public Order(Long orderId, Long customerId, Address address) {
		this.id = orderId;
		this.customerId = customerId;
		this.billingAddress = address;
		this.shippingAddress = address;
	}

	/**
	 * Returns the total of the [Order].
	 *
	 * @return
	 */
	public BigDecimal calcTotal() {
		if (lineItems.size() == 0) {
			return BigDecimal.ZERO;
		} else {
			return lineItems.stream().map(LineItem::calcTotal).reduce(BigDecimal::add).get();
		}
	}

	/**
	 * Adds the given [LineItem] to the [Order].
	 *
	 * @param lineItem
	 */
	public void add(LineItem lineItem) {
		lineItems.add(lineItem);
	}
}
