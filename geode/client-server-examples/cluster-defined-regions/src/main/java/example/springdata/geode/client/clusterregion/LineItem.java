/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.client.clusterregion;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A LineItem used in the examples
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
public class LineItem implements Serializable {

	private Product product;
	private Integer amount;

	public LineItem(Product product, Integer amount) {
		this.product = product;
		this.amount = amount;
	}


	public BigDecimal calcTotal() {
		return product.getPrice().multiply(BigDecimal.valueOf(amount));
	}

	@Override
	public String toString() {
		return "Purchased " + amount + " of Product " + product.getName() + " at " + product.getPrice() + " for total of " + calcTotal();
	}
}