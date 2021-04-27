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
package example.springdata.geode.server.storage;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.compression.Compressor;
import org.apache.geode.compression.SnappyCompressor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableOffHeap;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * @author Patrick Johnson
 */
@Configuration
@ComponentScan
@CacheServerApplication(logLevel = "error")
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableOffHeap(memorySize = "512m", regionNames = "Products")
public class StorageServerConfig {

	@Bean
	Compressor createSnappyCompressor() {
		return new SnappyCompressor();
	}

	@Bean
	RegionAttributesFactoryBean<Long, Order> regionAttributes(PartitionAttributes<Long, Order> partitionAttributes) {
		RegionAttributesFactoryBean<Long, Order> regionAttributesFactoryBean = new RegionAttributesFactoryBean<>();
		regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
		return regionAttributesFactoryBean;
	}

	@Bean
	PartitionAttributesFactoryBean<Long, Order> partitionAttributes() {
		PartitionAttributesFactoryBean<Long, Order> partitionAttributesFactoryBean = new PartitionAttributesFactoryBean<>();
		partitionAttributesFactoryBean.setTotalNumBuckets(11);
		partitionAttributesFactoryBean.setRedundantCopies(1);
		return partitionAttributesFactoryBean;
	}

	@Bean("Orders")
	PartitionedRegionFactoryBean<Long, Order> createOrderRegion(GemFireCache gemFireCache,
			RegionAttributes<Long, Order> regionAttributes) {
		PartitionedRegionFactoryBean<Long, Order> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("Orders");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setAttributes(regionAttributes);
		return partitionedRegionFactoryBean;
	}

	@Bean("Products")
	ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemFireCache) {
		ReplicatedRegionFactoryBean<Long, Product> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Products");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		return replicatedRegionFactoryBean;
	}

	@Bean("Customers")
	ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache, Compressor compressor) {
		ReplicatedRegionFactoryBean<Long, Customer> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setRegionName("Customers");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setCompressor(compressor);
		return replicatedRegionFactoryBean;
	}
}
