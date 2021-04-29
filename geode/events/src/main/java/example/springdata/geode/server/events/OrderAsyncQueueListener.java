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
package example.springdata.geode.server.events;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;

/**
 * @author Patrick Johnson
 */
public class OrderAsyncQueueListener implements AsyncEventListener {

	private Region<Long, OrderProductSummary> summaryRegion;

	public OrderAsyncQueueListener(Region<Long, OrderProductSummary> summaryRegion) {
		this.summaryRegion = summaryRegion;
	}

	@Override
	public boolean processEvents(List<AsyncEvent> list) {
		Map<Long, OrderProductSummary> summaryMap = new HashMap<>();
		list.forEach(asyncEvent -> {
			var order = (Order) asyncEvent.getDeserializedValue();
			if (order != null) {
				order.getLineItems().forEach(lineItem -> {
					var orderProductSummary = summaryMap.get(lineItem.getProductId());
					if (orderProductSummary == null) {
						orderProductSummary = new OrderProductSummary(lineItem.getProductId(), new BigDecimal("0.00"));
					}
					orderProductSummary.setSummaryAmount(orderProductSummary.getSummaryAmount().add(lineItem.calcTotal()));
					summaryMap.put(lineItem.getProductId(), orderProductSummary);
				});
			}
		});

		summaryMap.forEach((orderProductSummaryKey, orderProductSummary) -> {
			OrderProductSummary productSummary = summaryRegion.get(orderProductSummaryKey);
			if (productSummary != null) {
				BigDecimal newSummaryAmount = productSummary.getSummaryAmount().add(orderProductSummary.getSummaryAmount());
				summaryRegion.put(orderProductSummaryKey, new OrderProductSummary(orderProductSummaryKey, newSummaryAmount));
			} else {
				summaryRegion.put(orderProductSummaryKey, orderProductSummary);
			}
		});

		return true;
	}
}
