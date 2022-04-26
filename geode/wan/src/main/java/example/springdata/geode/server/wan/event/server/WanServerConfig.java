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
package example.springdata.geode.server.wan.event.server;

import example.springdata.geode.server.wan.event.Customer;
import example.springdata.geode.server.wan.event.CustomerRepository;
import example.springdata.geode.server.wan.event.server.siteA.SiteAWanEnabledServerConfig;
import example.springdata.geode.server.wan.event.server.siteB.SiteBWanServerConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.wan.GatewaySender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.DiskStoreFactoryBean;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import com.github.javafaker.Faker;

/**
 * @author Patrick Johnson
 */
@Configuration
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@Import({ SiteAWanEnabledServerConfig.class, SiteBWanServerConfig.class })
public class WanServerConfig {

	@Bean
	Faker faker() {
		return new Faker();
	}

	@Bean(name = "DiskStore")
	DiskStoreFactoryBean diskStore(GemFireCache gemFireCache, Faker faker) throws IOException {
		DiskStoreFactoryBean diskStoreFactoryBean = new DiskStoreFactoryBean();
		File tempDirectory = File.createTempFile("prefix-" + faker.name().firstName(),
				"suffix-" + faker.name().firstName());
		tempDirectory.delete();
		tempDirectory.mkdirs();
		tempDirectory.deleteOnExit();
		DiskStoreFactoryBean.DiskDir[] diskDirs = { new DiskStoreFactoryBean.DiskDir(tempDirectory.getAbsolutePath()) };
		diskStoreFactoryBean.setDiskDirs(Arrays.asList(diskDirs));
		diskStoreFactoryBean.setCache(gemFireCache);
		return diskStoreFactoryBean;
	}

	@Bean
	RegionAttributesFactoryBean<Long, Customer> regionAttributes(
			PartitionAttributes<Long, Customer> partitionAttributes) {
		RegionAttributesFactoryBean<Long, Customer> regionAttributesFactoryBean = new RegionAttributesFactoryBean<>();
		regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
		return regionAttributesFactoryBean;
	}

	@Bean
	PartitionAttributesFactoryBean<Long, Customer> partitionAttributes() {
		PartitionAttributesFactoryBean<Long, Customer> partitionAttributesFactoryBean = new PartitionAttributesFactoryBean<>();
		partitionAttributesFactoryBean.setTotalNumBuckets(13);
		partitionAttributesFactoryBean.setRedundantCopies(0);
		return partitionAttributesFactoryBean;
	}

	@Bean("Customers")
	PartitionedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache,
			RegionAttributes<Long, Customer> regionAttributes, GatewaySender gatewaySender) {
		PartitionedRegionFactoryBean<Long, Customer> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("Customers");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setAttributes(regionAttributes);
		partitionedRegionFactoryBean.setGatewaySenders(new GatewaySender[] { gatewaySender });
		return partitionedRegionFactoryBean;
	}
}
