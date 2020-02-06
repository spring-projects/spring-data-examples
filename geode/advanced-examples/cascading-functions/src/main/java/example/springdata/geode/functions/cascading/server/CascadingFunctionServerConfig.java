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
import example.springdata.geode.functions.cascading.Product;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions;

@Configuration
@CacheServerApplication(copyOnRead = true, port = 0, locators = "localhost[10334]", logLevel = "error")
@EnableGemfireFunctions
@EnableLocator()
@Import(CascadingFunctions.class)
public class CascadingFunctionServerConfig {

	@Bean("Customers")
	protected PartitionedRegionFactoryBean<Long, Customer> customerRegion(GemFireCache gemfireCache) {
		PartitionedRegionFactoryBean<Long, Customer> regionFactoryBean = new PartitionedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemfireCache);
		regionFactoryBean.setRegionName("Customers");
		regionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		return regionFactoryBean;
	}

	@Bean("Orders")
	protected PartitionedRegionFactoryBean<Long, Order> orderRegion(GemFireCache gemfireCache) {
		PartitionedRegionFactoryBean<Long, Order> regionFactoryBean = new PartitionedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemfireCache);
		regionFactoryBean.setRegionName("Orders");
		regionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		return regionFactoryBean;
	}

	@Bean("Products")
	protected PartitionedRegionFactoryBean<Long, Product> productsRegion(GemFireCache gemfireCache) {
		PartitionedRegionFactoryBean<Long, Product> regionFactoryBean = new PartitionedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemfireCache);
		regionFactoryBean.setRegionName("Products");
		regionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		return regionFactoryBean;
	}
}