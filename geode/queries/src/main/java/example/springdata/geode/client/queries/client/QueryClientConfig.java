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
package example.springdata.geode.client.queries.client;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.search.lucene.LuceneTemplate;

/**
 * @author Patrick Johnson
 */
@Configuration
@EnableGemfireRepositories
@ClientCacheApplication(name = "CQClientCache", logLevel = "error", pingInterval = 5000L, readTimeout = 15000,
		subscriptionEnabled = true, readyForEvents = true)
@EnableContinuousQueries
@EnableClusterDefinedRegions(clientRegionShortcut = ClientRegionShortcut.PROXY)
public class QueryClientConfig {

	@Bean("customerTemplate")
	@DependsOn("Customers")
	protected GemfireTemplate configureCustomerTemplate(GemFireCache gemfireCache) {
		return new GemfireTemplate(gemfireCache.getRegion("Customers"));
	}

	@Bean
	LuceneTemplate createCustomerLuceneTemplate() {
		return new LuceneTemplate("lastName_lucene", "/Customers");
	}
}
