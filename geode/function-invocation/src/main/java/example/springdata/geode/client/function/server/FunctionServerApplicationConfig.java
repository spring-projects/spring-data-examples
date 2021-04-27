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
package example.springdata.geode.client.function.server;

import example.springdata.geode.client.function.Customer;
import example.springdata.geode.client.function.Order;
import example.springdata.geode.client.function.Product;
import example.springdata.geode.client.function.client.CustomerRepository;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableIndexing;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * @author Patrick Johnson
 */
@Configuration
@ComponentScan(basePackageClasses = CustomerFunctions.class)
@EnableGemfireFunctions
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableLocator
@EnableIndexing
@EnableManager
@CacheServerApplication(port = 0, logLevel = "error")
public class FunctionServerApplicationConfig {

	@Bean("Customers")
	ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemfireCache) {
		ReplicatedRegionFactoryBean<Long, Customer> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemfireCache);
		replicatedRegionFactoryBean.setRegionName("Customers");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		replicatedRegionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		return replicatedRegionFactoryBean;
	}

	@Bean("Orders")
	ReplicatedRegionFactoryBean<Long, Order> createOrderRegion(GemFireCache gemfireCache) {
		ReplicatedRegionFactoryBean<Long, Order> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemfireCache);
		replicatedRegionFactoryBean.setRegionName("Orders");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		return replicatedRegionFactoryBean;
	}

	@Bean("Products")
	ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemfireCache) {
		ReplicatedRegionFactoryBean<Long, Product> replicatedRegionFactoryBean = new ReplicatedRegionFactoryBean<>();
		replicatedRegionFactoryBean.setCache(gemfireCache);
		replicatedRegionFactoryBean.setRegionName("Products");
		replicatedRegionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		return replicatedRegionFactoryBean;
	}
}
