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
package example.springdata.geode.server.wan.event.client;

import example.springdata.geode.server.wan.event.Customer;
import example.springdata.geode.server.wan.event.CustomerRepository;

import java.util.Collections;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheConfigurer;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.support.ConnectionEndpoint;

/**
 * Spring JavaConfig configuration class to setup a Spring container and infrastructure components.
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Configuration
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@ClientCacheApplication(name = "WanClient", logLevel = "error", pingInterval = 5000L, readTimeout = 15000,
		retryAttempts = 1)
public class WanClientConfig {

	@Bean("Customers")
	protected ClientRegionFactoryBean<Long, Customer> configureProxyClientCustomerRegion(GemFireCache gemFireCache) {
		ClientRegionFactoryBean<Long, Customer> clientRegionFactoryBean = new ClientRegionFactoryBean<>();
		clientRegionFactoryBean.setCache(gemFireCache);
		clientRegionFactoryBean.setName("Customers");
		clientRegionFactoryBean.setShortcut(ClientRegionShortcut.PROXY);
		return clientRegionFactoryBean;
	}

	@Bean
	ClientCacheConfigurer clientCacheServerConfigurer(
			@Value("${spring.data.geode.locator.host:localhost}") String hostname,
			@Value("${spring.data.geode.locator.port:10334}") int port) {

		return (beanName, clientCacheFactoryBean) -> clientCacheFactoryBean
				.setLocators(Collections.singletonList(new ConnectionEndpoint(hostname, port)));
	}
}
