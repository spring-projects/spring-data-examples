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
package example.springdata.geode.server.wan.event.server.siteB;

import example.springdata.geode.server.wan.event.Customer;
import example.springdata.geode.server.wan.event.server.EvenNumberedKeyWanEventFilter;
import example.springdata.geode.server.wan.event.server.WanEventSubstitutionFilter;
import example.springdata.geode.server.wan.event.server.WanTransportEncryptionListener;

import java.util.Collections;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.wan.GatewayEventFilter;
import org.apache.geode.cache.wan.GatewayEventSubstitutionFilter;
import org.apache.geode.cache.wan.GatewayTransportFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableGemFireProperties;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.wan.GatewayReceiverFactoryBean;
import org.springframework.data.gemfire.wan.GatewaySenderFactoryBean;

/**
 * @author Patrick Johnson
 */
@Configuration
@CacheServerApplication(port = 0, locators = "localhost[20334]", name = "SiteB_Server", logLevel = "error")
@Profile("SiteB")
@EnableLocator(port = 20334)
@EnableGemFireProperties(distributedSystemId = 2, remoteLocators = "localhost[10334]")
@Import({ EvenNumberedKeyWanEventFilter.class, WanEventSubstitutionFilter.class, WanTransportEncryptionListener.class })
public class SiteBWanServerConfig {

	@Bean
	GatewayReceiverFactoryBean createGatewayReceiver(GemFireCache gemFireCache) {
		GatewayReceiverFactoryBean gatewayReceiverFactoryBean = new GatewayReceiverFactoryBean((Cache) gemFireCache);
		gatewayReceiverFactoryBean.setStartPort(25000);
		gatewayReceiverFactoryBean.setEndPort(25010);
		return gatewayReceiverFactoryBean;
	}

	@Bean
	@DependsOn("DiskStore")
	GatewaySenderFactoryBean createGatewaySender(GemFireCache gemFireCache, GatewayEventFilter gatewayEventFilter,
			GatewayTransportFilter gatewayTransportFilter,
			GatewayEventSubstitutionFilter<Long, Customer> gatewayEventSubstitutionFilter) {
		GatewaySenderFactoryBean gatewaySenderFactoryBean = new GatewaySenderFactoryBean(gemFireCache);
		gatewaySenderFactoryBean.setBatchSize(15);
		gatewaySenderFactoryBean.setBatchTimeInterval(1000);
		gatewaySenderFactoryBean.setRemoteDistributedSystemId(1);
		gatewaySenderFactoryBean.setDiskStoreRef("DiskStore");
		gatewaySenderFactoryBean.setEventFilters(Collections.singletonList(gatewayEventFilter));
		gatewaySenderFactoryBean.setTransportFilters(Collections.singletonList(gatewayTransportFilter));
		gatewaySenderFactoryBean.setEventSubstitutionFilter(gatewayEventSubstitutionFilter);
		gatewaySenderFactoryBean.setPersistent(false);
		return gatewaySenderFactoryBean;
	}
}
