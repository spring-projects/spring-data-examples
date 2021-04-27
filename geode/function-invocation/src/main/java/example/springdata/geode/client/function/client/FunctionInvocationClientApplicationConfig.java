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
package example.springdata.geode.client.function.client;

import example.springdata.geode.client.function.Customer;
import example.springdata.geode.client.function.Order;
import example.springdata.geode.client.function.Product;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;

/**
 * Spring JavaConfig configuration class to setup a Spring container and infrastructure components.
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Configuration
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableGemfireFunctionExecutions(basePackageClasses = CustomerFunctionExecutions.class)
@ClientCacheApplication(name = "FunctionInvocationClient", logLevel = "error", pingInterval = 5000L,
		readTimeout = 15000, retryAttempts = 1)
@EnableGemfireCacheTransactions
public class FunctionInvocationClientApplicationConfig {

	@Bean("Customers")
	protected ClientRegionFactoryBean<Long, Customer> configureProxyClientCustomerRegion(GemFireCache gemFireCache) {
		ClientRegionFactoryBean<Long, Customer> clientRegionFactoryBean = new ClientRegionFactoryBean<>();
		clientRegionFactoryBean.setCache(gemFireCache);
		clientRegionFactoryBean.setName("Customers");
		clientRegionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return clientRegionFactoryBean;
	}

	@Bean("Products")
	protected ClientRegionFactoryBean<Long, Product> configureProxyClientProductRegion(GemFireCache gemFireCache) {
		ClientRegionFactoryBean<Long, Product> clientRegionFactoryBean = new ClientRegionFactoryBean<>();
		clientRegionFactoryBean.setCache(gemFireCache);
		clientRegionFactoryBean.setName("Products");
		clientRegionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return clientRegionFactoryBean;
	}

	@Bean("Orders")
	protected ClientRegionFactoryBean<Long, Order> configureProxyClientOrderRegion(GemFireCache gemFireCache) {
		ClientRegionFactoryBean<Long, Order> clientRegionFactoryBean = new ClientRegionFactoryBean<>();
		clientRegionFactoryBean.setCache(gemFireCache);
		clientRegionFactoryBean.setName("Orders");
		clientRegionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return clientRegionFactoryBean;
	}
}
