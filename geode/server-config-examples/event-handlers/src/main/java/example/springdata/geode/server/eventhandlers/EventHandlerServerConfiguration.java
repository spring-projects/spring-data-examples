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

package example.springdata.geode.server.eventhandlers;

import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.CacheWriter;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@CacheServerApplication(logLevel = "error")
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class EventHandlerServerConfiguration {

	@Bean
	CacheWriter<Long, Customer> customerCacheWriter() {
		return new CustomerCacheWriter();
	}

	@Bean
	CacheLoader<Long, Product> productCacheLoader() {
		return new ProductCacheLoader();
	}

	@Bean("Products")
	ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemFireCache, CacheListener<Long, Product> loggingCacheListener,
																   CacheLoader<Long, Product> productCacheLoader) {
		final ReplicatedRegionFactoryBean<Long, Product> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Products");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setCacheListeners(new CacheListener[]{loggingCacheListener});
		replicatedRegionFactoryBean.setCacheLoader(productCacheLoader);
		return replicatedRegionFactoryBean;
	}

	@Bean("Customers")
	PartitionedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache,
																	  CacheWriter<Long, Customer> customerCacheWriter, CacheListener<Long, Customer> loggingCacheListener) {
		final PartitionedRegionFactoryBean<Long, Customer> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("Customers");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setCacheListeners(new CacheListener[]{loggingCacheListener});
		partitionedRegionFactoryBean.setCacheWriter(customerCacheWriter);
		return partitionedRegionFactoryBean;
	}
}