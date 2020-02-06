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

import org.apache.geode.cache.Region;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class OrderAsyncQueueListener implements AsyncEventListener {

	private Region summaryRegion;

	public OrderAsyncQueueListener(Region summaryRegion) {
		this.summaryRegion = summaryRegion;
	}

	@Override
	public boolean processEvents(List<AsyncEvent> list) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		HashMap<OrderProductSummaryKey, OrderProductSummary> summaryMap = new HashMap<>();
		list.forEach(asyncEvent -> {
			final Order order = (Order) asyncEvent.getDeserializedValue();
			if (order != null) {
				order.getLineItems().forEach(lineItem -> {
					final OrderProductSummaryKey key = new OrderProductSummaryKey(lineItem.getProductId(), calendar.getTimeInMillis());
					OrderProductSummary orderProductSummary = summaryMap.get(key);
					if (orderProductSummary == null) {
						orderProductSummary = new OrderProductSummary(key, new BigDecimal("0.00"));
					}
					orderProductSummary.setSummaryAmount(orderProductSummary.getSummaryAmount().add(lineItem.calcTotal()));
					summaryMap.put(key, orderProductSummary);
				});
			}
		});

		summaryMap.forEach((orderProductSummaryKey, orderProductSummary) -> {
			final OrderProductSummary productSummary = (OrderProductSummary) summaryRegion.get(orderProductSummaryKey);
			if (productSummary != null) {
				final BigDecimal newSummaryAmount = productSummary.getSummaryAmount().add(orderProductSummary.getSummaryAmount());
				summaryRegion.put(orderProductSummaryKey, new OrderProductSummary(orderProductSummaryKey, newSummaryAmount));
			} else {
				summaryRegion.put(orderProductSummaryKey, orderProductSummary);
			}
		});
		return true;
	}
}