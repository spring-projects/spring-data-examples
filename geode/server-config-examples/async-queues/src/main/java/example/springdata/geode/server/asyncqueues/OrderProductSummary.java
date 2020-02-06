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

package example.springdata.geode.server.asyncqueues;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * OrderProductSummary is an object used in the examples to show a summary of all Orders on a per product basis, on a per
 * timeframe shard (every 10s, or every 1hr)
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Region("OrderProductSummary")
public class OrderProductSummary implements Serializable {

	@Id
	private OrderProductSummaryKey summaryKey;

	private BigDecimal summaryAmount;

	public OrderProductSummary(OrderProductSummaryKey summaryKey, BigDecimal summaryAmount) {
		this.summaryKey = summaryKey;
		this.summaryAmount = summaryAmount;
	}

	public BigDecimal getSummaryAmount() {
		return summaryAmount;
	}

	public void setSummaryAmount(BigDecimal summaryAmount) {
		this.summaryAmount = summaryAmount;
	}
}