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

package example.springdata.geode.server.region;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.RegionAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@CacheServerApplication(logLevel = "error")
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class RegionTypeConfiguration {

	@Bean
	RegionAttributesFactoryBean<Long, Customer> regionAttributes(PartitionAttributes<Long, Customer> partitionAttributes) {
		final RegionAttributesFactoryBean<Long, Customer> regionAttributesFactoryBean = new RegionAttributesFactoryBean<>();
		regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
		return regionAttributesFactoryBean;
	}

	@Bean
	PartitionAttributesFactoryBean<Long, Order> partitionAttributes() {
		final PartitionAttributesFactoryBean<Long, Order> partitionAttributesFactoryBean = new PartitionAttributesFactoryBean<>();
		partitionAttributesFactoryBean.setTotalNumBuckets(13);
		partitionAttributesFactoryBean.setRedundantCopies(1);
		return partitionAttributesFactoryBean;
	}

	@Bean("Orders")
	ReplicatedRegionFactoryBean<Long, Order> createOrderRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Order> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Orders");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		return replicatedRegionFactoryBean;
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
	PartitionedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache, RegionAttributes<Long, Customer> regionAttributes) {
		final PartitionedRegionFactoryBean<Long, Customer> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("Customers");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setAttributes(regionAttributes);
		return partitionedRegionFactoryBean;
	}
}