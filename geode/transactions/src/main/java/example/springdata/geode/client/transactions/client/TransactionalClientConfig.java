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
package example.springdata.geode.client.transactions.client;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Patrick Johnson
 */
@EnableClusterDefinedRegions(clientRegionShortcut = ClientRegionShortcut.PROXY)
@EnableTransactionManagement
@EnableGemfireCacheTransactions
@Configuration
@EnablePdx
@ComponentScan(basePackageClasses = CustomerService.class)
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@ClientCacheApplication(name = "TransactionalClient", logLevel = "error", pingInterval = 5000L, readTimeout = 15000,
		retryAttempts = 1)
public class TransactionalClientConfig {}
