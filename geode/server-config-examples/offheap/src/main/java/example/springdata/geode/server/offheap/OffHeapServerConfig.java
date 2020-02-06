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

package example.springdata.geode.server.offheap;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableOffHeap;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@CacheServerApplication(logLevel = "error")
@EnableLocator
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableOffHeap(memorySize = "8192m", regionNames = {"Customers"})
public class OffHeapServerConfig {

	@Bean("Customers")
	public ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Customer> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemFireCache);
		regionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		regionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		regionFactoryBean.setName("Customers");
		regionFactoryBean.setStatisticsEnabled(true);
		return regionFactoryBean;
	}

	@Bean("Products")
	public ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Product> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemFireCache);
		regionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		regionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		regionFactoryBean.setName("Products");
		regionFactoryBean.setStatisticsEnabled(true);
		regionFactoryBean.setOffHeap(true);
		return regionFactoryBean;
	}
}