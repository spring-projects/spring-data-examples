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

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.CacheWriter;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;

@Configuration
@ComponentScan
@CacheServerApplication(logLevel = "error")
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class EventServerConfig {

	@Bean
	AsyncEventListener orderAsyncEventListener(@Qualifier("OrderProductSummary") Region<Long, OrderProductSummary> orderProductSummary) {
		return new OrderAsyncQueueListener(orderProductSummary);
	}

	@Bean
	AsyncEventQueueFactoryBean orderAsyncEventQueue(GemFireCache gemFireCache, AsyncEventListener orderAsyncEventListener) {
		var asyncEventQueueFactoryBean = new AsyncEventQueueFactoryBean((Cache) gemFireCache);
		asyncEventQueueFactoryBean.setBatchTimeInterval(1000);
		asyncEventQueueFactoryBean.setBatchSize(5);
		asyncEventQueueFactoryBean.setAsyncEventListener(orderAsyncEventListener);
		return asyncEventQueueFactoryBean;
	}

	@Bean(name = "OrderProductSummary")
	PartitionedRegionFactoryBean<Long, Order> createOrderProductSummaryRegion(GemFireCache gemFireCache) {
		PartitionedRegionFactoryBean<Long, Order> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("OrderProductSummary");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		return partitionedRegionFactoryBean;
	}

	@Bean("Orders")
	PartitionedRegionFactoryBean<Long, Order> createOrderRegion(GemFireCache gemFireCache, AsyncEventQueue orderAsyncEventQueue) {
		PartitionedRegionFactoryBean<Long, Order> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("Orders");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setAsyncEventQueues(new AsyncEventQueue[]{orderAsyncEventQueue});
		return partitionedRegionFactoryBean;
	}

	@Bean("Products")
	ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemFireCache, CacheListener<Long, Product> loggingCacheListener,
																   CacheLoader<Long, Product> productCacheLoader) {
		ReplicatedRegionFactoryBean<Long, Product> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Products");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setCacheLoader(productCacheLoader);
		replicatedRegionFactoryBean.setCacheListeners(new CacheListener[]{loggingCacheListener});
		return replicatedRegionFactoryBean;
	}

	@Bean("Customers")
	ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache,
																	 CacheWriter<Long, Customer> customerCacheWriter,
																	 CacheListener<Long, Customer> loggingCacheListener) {
		ReplicatedRegionFactoryBean<Long, Customer> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Customers");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setCacheListeners(new CacheListener[]{loggingCacheListener});
		replicatedRegionFactoryBean.setCacheWriter(customerCacheWriter);
		return replicatedRegionFactoryBean;
	}
}
