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

package example.springdata.geode.server.wan.server.siteA;

import com.github.javafaker.Faker;
import example.springdata.geode.server.wan.Customer;
import example.springdata.geode.server.wan.CustomerRepository;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.wan.GatewaySender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.DiskStoreFactoryBean;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableGemFireProperties;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.wan.GatewayReceiverFactoryBean;
import org.springframework.data.gemfire.wan.GatewaySenderFactoryBean;

import java.io.File;
import java.util.Arrays;

@Configuration
@CacheServerApplication(port = 0, locators = "localhost[10334]", name = "SiteA_Server", logLevel = "error")
@Profile({"default", "SiteA"})
@EnableLocator
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableGemFireProperties(distributedSystemId = 1, remoteLocators = "localhost[20334]")
public class SiteAWanEnabledServerConfig {
	@Bean
	GatewayReceiverFactoryBean createGatewayReceiver(GemFireCache gemFireCache) {
		final GatewayReceiverFactoryBean gatewayReceiverFactoryBean = new GatewayReceiverFactoryBean((Cache) gemFireCache);
		gatewayReceiverFactoryBean.setStartPort(15000);
		gatewayReceiverFactoryBean.setEndPort(15010);
		gatewayReceiverFactoryBean.setManualStart(false);
		return gatewayReceiverFactoryBean;
	}

	@Bean
	@DependsOn("DiskStore")
	GatewaySenderFactoryBean createGatewaySender(GemFireCache gemFireCache) {
		final GatewaySenderFactoryBean gatewaySenderFactoryBean = new GatewaySenderFactoryBean(gemFireCache);
		gatewaySenderFactoryBean.setBatchSize(15);
		gatewaySenderFactoryBean.setBatchTimeInterval(1000);
		gatewaySenderFactoryBean.setRemoteDistributedSystemId(2);
		gatewaySenderFactoryBean.setPersistent(false);
		gatewaySenderFactoryBean.setDiskStoreRef("DiskStore");
		return gatewaySenderFactoryBean;
	}

	@Bean
	Faker faker() {
		return new Faker();
	}

	@Bean(name = "DiskStore")
	DiskStoreFactoryBean diskStore(GemFireCache gemFireCache, Faker faker) {
		final DiskStoreFactoryBean diskStoreFactoryBean = new DiskStoreFactoryBean();
		final boolean completed = new File("/tmp/" + faker.name().firstName()).mkdirs();
		final DiskStoreFactoryBean.DiskDir[] diskDirs = {new DiskStoreFactoryBean.DiskDir("/tmp/" + faker.name().firstName())};
		diskStoreFactoryBean.setDiskDirs(Arrays.asList(diskDirs));
		diskStoreFactoryBean.setCache(gemFireCache);
		return diskStoreFactoryBean;
	}

	@Bean
	RegionAttributesFactoryBean<Long, Customer> regionAttributes(PartitionAttributes<Long, Customer> partitionAttributes) {
		final RegionAttributesFactoryBean<Long, Customer> regionAttributesFactoryBean = new RegionAttributesFactoryBean<>();
		regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
		return regionAttributesFactoryBean;
	}

	@Bean
	PartitionAttributesFactoryBean<Long, Customer> partitionAttributes() {
		final PartitionAttributesFactoryBean<Long, Customer> partitionAttributesFactoryBean = new PartitionAttributesFactoryBean<>();
		partitionAttributesFactoryBean.setTotalNumBuckets(13);
		partitionAttributesFactoryBean.setRedundantCopies(0);
		return partitionAttributesFactoryBean;
	}

	@Bean("Customers")
	PartitionedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache, RegionAttributes<Long, Customer> regionAttributes, GatewaySender gatewaySender) {
		final PartitionedRegionFactoryBean<Long, Customer> partitionedRegionFactoryBean = new PartitionedRegionFactoryBean<>();
		partitionedRegionFactoryBean.setCache(gemFireCache);
		partitionedRegionFactoryBean.setRegionName("Customers");
		partitionedRegionFactoryBean.setDataPolicy(DataPolicy.PARTITION);
		partitionedRegionFactoryBean.setAttributes(regionAttributes);
		partitionedRegionFactoryBean.setGatewaySenders(new GatewaySender[]{gatewaySender});
		return partitionedRegionFactoryBean;
	}
}