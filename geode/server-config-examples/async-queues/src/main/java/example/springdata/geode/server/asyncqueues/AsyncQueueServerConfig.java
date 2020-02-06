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

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;

@Configuration
@CacheServerApplication(logLevel = "error")
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class AsyncQueueServerConfig {

	@Bean
	AsyncEventListener orderAsyncEventListener(@Qualifier("OrderProductSummary") Region<Long, Order> orderProductSummary) {
		return new OrderAsyncQueueListener(orderProductSummary);
	}

	@Bean
	AsyncEventQueueFactoryBean orderAsyncEventQueue(GemFireCache gemFireCache, AsyncEventListener orderAsyncEventListener) {
		final AsyncEventQueueFactoryBean asyncEventQueueFactoryBean = new AsyncEventQueueFactoryBean((Cache) gemFireCache);
		asyncEventQueueFactoryBean.setBatchTimeInterval(1000);
		asyncEventQueueFactoryBean.setBatchSize(5);
		asyncEventQueueFactoryBean.setAsyncEventListener(orderAsyncEventListener);
		return asyncEventQueueFactoryBean;
	}

	@Bean
	RegionAttributesFactoryBean<Long, Order> regionAttributes(PartitionAttributes<Long, Order> partitionAttributes) {
		final RegionAttributesFactoryBean<Long, Order> regionAttributesFactoryBean = new RegionAttributesFactoryBean<>();
		regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
		return regionAttributesFactoryBean;
	}

	@Bean
	PartitionAttributesFactoryBean<Long, Order> partitionAttributes() {
		final PartitionAttributesFactoryBean<Long, Order> partitionAttributesFactoryBean = new PartitionAttributesFactoryBean<>();
		partitionAttributesFactoryBean.setTotalNumBuckets(13);
		partitionAttributesFactoryBean.setRedundantCopies(0);
		return partitionAttributesFactoryBean;
	}

	@Bean(name = "OrderProductSummary")
	PartitionedRegionFactoryBean<Long, Order> createOrderProductSummaryRegion(GemFireCache gemFireCache, RegionAttributes<Long, Order> regionAttributes) {
		final PartitionedRegionFactoryBean<Long, Order> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("OrderProductSummary");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setAttributes(regionAttributes);
		return partitionedRegionFactoryBean;
	}

	@Bean("Orders")
	PartitionedRegionFactoryBean<Long, Order> createOrderRegion(GemFireCache gemFireCache, RegionAttributes<Long, Order> regionAttributes, AsyncEventQueue orderAsyncEventQueue) {
		final PartitionedRegionFactoryBean<Long, Order> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("Orders");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setAttributes(regionAttributes);
		partitionedRegionFactoryBean.setAsyncEventQueues(new AsyncEventQueue[]{orderAsyncEventQueue});
		return partitionedRegionFactoryBean;
	}

	@Bean("Products")
	ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Product> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Products");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		return replicatedRegionFactoryBean;
	}

	@Bean("Customers")
	ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Customer> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Customers");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		return replicatedRegionFactoryBean;
	}
}