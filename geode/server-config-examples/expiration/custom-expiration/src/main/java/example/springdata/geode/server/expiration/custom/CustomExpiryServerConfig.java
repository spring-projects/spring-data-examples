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

package example.springdata.geode.server.expiration.custom;

import com.github.javafaker.Faker;
import org.apache.geode.cache.CustomExpiry;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Scope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@CacheServerApplication(logLevel = "error")
@EnableLocator
@EnableGemfireRepositories(basePackageClasses = CustomerRepository.class)
public class CustomExpiryServerConfig {

	@Bean
	public Faker createDataFaker() {
		return new Faker();
	}

	@Bean("IDLE")
	CustomExpiry<Long, Customer> createIdleExpiration() {
		return new CustomCustomerExpiry(2);
	}

	@Bean("TTL")
	CustomExpiry<Long, Customer> createTtlExpiration() {
		return new CustomCustomerExpiry(4);
	}

	@Bean("Customers")
	public ReplicatedRegionFactoryBean<Long, Customer> createCustomerRegion(GemFireCache gemFireCache,
																			@Qualifier("IDLE") CustomExpiry idleExpiry,
																			@Qualifier("TTL") CustomExpiry ttlExpiry) {
		final ReplicatedRegionFactoryBean<Long, Customer> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
		regionFactoryBean.setCache(gemFireCache);
		regionFactoryBean.setScope(Scope.DISTRIBUTED_ACK);
		regionFactoryBean.setDataPolicy(DataPolicy.REPLICATE);
		regionFactoryBean.setName("Customers");
		regionFactoryBean.setStatisticsEnabled(true);
		regionFactoryBean.setCustomEntryIdleTimeout(idleExpiry);
		regionFactoryBean.setCustomEntryTimeToLive(ttlExpiry);
		return regionFactoryBean;
	}
}