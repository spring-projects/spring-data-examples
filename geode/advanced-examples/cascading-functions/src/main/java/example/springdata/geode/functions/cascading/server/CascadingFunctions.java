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

package example.springdata.geode.functions.cascading.server;

import example.springdata.geode.functions.cascading.Customer;
import example.springdata.geode.functions.cascading.Order;
import org.apache.geode.cache.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.data.gemfire.function.annotation.RegionData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CascadingFunctions {

	private Region<Long, Order> orderData;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public CascadingFunctions(@Qualifier("Orders") Region<Long, Order> orderData) {
		this.orderData = orderData;
	}

	@GemfireFunction(id = "ListAllCustomers", HA = true, optimizeForWrite = false, batchSize = 0, hasResult = true)
	public List<Long> listAllCustomers(@RegionData Map<Long, Customer> customerData) {
		logger.info("I'm executing function: \"listAllCustomers\" size= " + customerData.size());
		return customerData.keySet().stream().filter(customerId -> customerId != null).collect(Collectors.toList());
	}

	@GemfireFunction(id = "FindOrdersForCustomers", HA = true, optimizeForWrite = true, batchSize = 0, hasResult = true)
	public List<Order> findOrdersForCustomers(List<Order> customerIds) {
		logger.info("I'm executing function: \"findOrdersForCustomer\" size= " + orderData.size());
		List<Order> returnValue = orderData.values().stream().filter(order -> customerIds.contains(order.getCustomerId())).collect(Collectors.toList());
		logger.info("Return Value: " + returnValue);
		return returnValue;
	}
}