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

package example.springdata.geode.functions.cascading.client;

import example.springdata.geode.functions.cascading.Customer;
import example.springdata.geode.functions.cascading.Order;
import example.springdata.geode.functions.cascading.Product;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ClientCacheApplication(name = "CascadingFunctionClientCache", logLevel = "error", pingInterval = 5000L, readTimeout = 15000, retryAttempts = 1)
@EnableGemfireFunctionExecutions(basePackageClasses = CustomerFunctionExecutions.class)
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableGemfireCacheTransactions
@EnableTransactionManagement
public class CascadingFunctionClientConfig {
	@Bean("Customers")
	protected ClientRegionFactoryBean<Long, Customer> customerRegion(GemFireCache gemfireCache) {
		ClientRegionFactoryBean<Long, Customer> regionFactoryBean = new ClientRegionFactoryBean<>();
		regionFactoryBean.setCache(gemfireCache);
		regionFactoryBean.setRegionName("Customers");
		regionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return regionFactoryBean;
	}

	@Bean("Orders")
	protected ClientRegionFactoryBean<Long, Order> orderRegion(GemFireCache gemfireCache) {
		ClientRegionFactoryBean<Long, Order> regionFactoryBean = new ClientRegionFactoryBean<>();
		regionFactoryBean.setCache(gemfireCache);
		regionFactoryBean.setRegionName("Orders");
		regionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return regionFactoryBean;
	}

	@Bean("Products")
	protected ClientRegionFactoryBean<Long, Product> productsRegion(GemFireCache gemfireCache) {
		ClientRegionFactoryBean<Long, Product> regionFactoryBean = new ClientRegionFactoryBean<>();
		regionFactoryBean.setCache(gemfireCache);
		regionFactoryBean.setRegionName("Products");
		regionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return regionFactoryBean;
	}
}