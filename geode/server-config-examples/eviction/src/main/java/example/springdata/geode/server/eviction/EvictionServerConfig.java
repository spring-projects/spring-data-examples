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

package example.springdata.geode.server.eviction;

import com.github.javafaker.Faker;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.EvictionAction;
import org.apache.geode.cache.EvictionAttributes;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;
import org.apache.geode.cache.util.ObjectSizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.DiskStoreFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableEviction;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.eviction.EvictionActionType;
import org.springframework.data.gemfire.eviction.EvictionPolicyType;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import java.io.File;
import java.util.Collections;

@Configuration
@CacheServerApplication(criticalHeapPercentage = 0.7f, evictionHeapPercentage = 0.4f, logLevel = "error")
@EnableLocator
@EnableEviction(policies = @EnableEviction.EvictionPolicy(regionNames = "Orders",
		maximum = 10,
		action = EvictionActionType.LOCAL_DESTROY,
		type = EvictionPolicyType.ENTRY_COUNT))
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class EvictionServerConfig {
	@Bean
	public Faker faker() {
		return new Faker();
	}

	@Bean("DiskStoreDir")
	public File diskStoreDir(Faker faker) {
		final File file = new File("/tmp/" + faker.name().firstName());
		file.deleteOnExit();
		file.mkdirs();
		return file;
	}

	@Bean(name = "DiskStore")
	@DependsOn("DiskStoreDir")
	public DiskStoreFactoryBean diskStore(GemFireCache gemFireCache, @Qualifier("DiskStoreDir") File diskStoreDir) {
		final DiskStoreFactoryBean diskStoreFactoryBean = new DiskStoreFactoryBean();
		diskStoreFactoryBean.setDiskDirs(Collections.singletonList(new DiskStoreFactoryBean.DiskDir(diskStoreDir.getPath())));
		diskStoreFactoryBean.setCache(gemFireCache);
		return diskStoreFactoryBean;
	}

	@Bean("Orders")
	public ReplicatedRegionFactoryBean<Long, Order> createOrderRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Order> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setScope(Scope.DISTRIBUTED_NO_ACK);
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setName("Orders");
		replicatedRegionFactoryBean.setDiskStoreName("DiskStore");
		return replicatedRegionFactoryBean;
	}

	@Bean("Customers")
	public ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Customer> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setScope(Scope.DISTRIBUTED_NO_ACK);
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setName("Customers");
		replicatedRegionFactoryBean.setDiskStoreName("DiskStore");
		replicatedRegionFactoryBean.setEvictionAttributes(
				EvictionAttributes.createLRUHeapAttributes(ObjectSizer.DEFAULT, EvictionAction.OVERFLOW_TO_DISK));
		return replicatedRegionFactoryBean;
	}

	@Bean("Products")
	public ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemFireCache) {
		final ReplicatedRegionFactoryBean<Long, Product> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemFireCache);
		replicatedRegionFactoryBean.setScope(Scope.DISTRIBUTED_NO_ACK);
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setName("Products");
		replicatedRegionFactoryBean.setDiskStoreName("DiskStore");
		replicatedRegionFactoryBean.setEvictionAttributes(
				EvictionAttributes.createLRUEntryAttributes(100, EvictionAction.OVERFLOW_TO_DISK));
		return replicatedRegionFactoryBean;
	}
}