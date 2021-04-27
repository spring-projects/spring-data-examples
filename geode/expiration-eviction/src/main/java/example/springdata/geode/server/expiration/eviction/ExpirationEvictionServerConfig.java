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
package example.springdata.geode.server.expiration.eviction;

import org.apache.geode.cache.CustomExpiry;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableEviction;
import org.springframework.data.gemfire.config.annotation.EnableExpiration;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.eviction.EvictionActionType;
import org.springframework.data.gemfire.eviction.EvictionPolicyType;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import com.github.javafaker.Faker;

/**
 * @author Patrick Johnson
 */
@Configuration
@CacheServerApplication(logLevel = "error")
@EnableLocator
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
@EnableExpiration
@Import(ExpirationPolicyConfig.class)
@EnableEviction(policies = @EnableEviction.EvictionPolicy(regionNames = "Orders", maximum = 10,
		action = EvictionActionType.LOCAL_DESTROY, type = EvictionPolicyType.ENTRY_COUNT))
public class ExpirationEvictionServerConfig {

	@Bean
	public Faker createDataFaker() {
		return new Faker();
	}

	@Bean("IDLE")
	CustomExpiry<Long, Product> createIdleExpiration() {
		return new CustomCustomerExpiry(2);
	}

	@Bean("TTL")
	CustomExpiry<Long, Product> createTtlExpiration() {
		return new CustomCustomerExpiry(4);
	}

	@Bean("Products")
	public ReplicatedRegionFactoryBean<Long, Product> createProductRegion(GemFireCache gemFireCache,
			@Qualifier("IDLE") CustomExpiry<Long, Product> idleExpiry,
			@Qualifier("TTL") CustomExpiry<Long, Product> ttlExpiry) {
		ReplicatedRegionFactoryBean<Long, Product> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemFireCache);
		regionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		regionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		regionFactoryBean.setName("Products");
		regionFactoryBean.setCustomEntryIdleTimeout(idleExpiry);
		regionFactoryBean.setCustomEntryTimeToLive(ttlExpiry);
		return regionFactoryBean;
	}

	@Bean("Customers")
	public ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache) {
		ReplicatedRegionFactoryBean<Long, Customer> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemFireCache);
		regionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		regionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		regionFactoryBean.setName("Customers");
		return regionFactoryBean;
	}

	@Bean("Orders")
	public ReplicatedRegionFactoryBean<Long, Order> createOrderRegion(GemFireCache gemFireCache) {
		ReplicatedRegionFactoryBean<Long, Order> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemFireCache);
		regionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		regionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		regionFactoryBean.setName("Orders");
		return regionFactoryBean;
	}
}
