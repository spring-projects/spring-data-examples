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

package example.springdata.geode.server.wan.transport.server.siteB;

import example.springdata.geode.server.wan.transport.server.WanTransportEncryptionListener;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.GemFireCache;
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

import java.util.Collections;

@Configuration
@CacheServerApplication(logLevel = "error")
@Profile("SiteB")
@EnableLocator(port = 20334)
@EnableGemFireProperties(distributedSystemId = 2, remoteLocators = "localhost[10334]")
@Import(WanTransportEncryptionListener.class)
public class SiteBWanTransportListenerServerConfig {
	@Bean
	GatewayReceiverFactoryBean createGatewayReceiver(GemFireCache gemFireCache) {
		final GatewayReceiverFactoryBean gatewayReceiverFactoryBean = new GatewayReceiverFactoryBean((Cache) gemFireCache);
		gatewayReceiverFactoryBean.setStartPort(25000);
		gatewayReceiverFactoryBean.setEndPort(25010);
		return gatewayReceiverFactoryBean;
	}

	@Bean
	@DependsOn("DiskStore")
	GatewaySenderFactoryBean createGatewaySender(GemFireCache gemFireCache, GatewayTransportFilter gatewayTransportFilter) {
		final GatewaySenderFactoryBean gatewaySenderFactoryBean = new GatewaySenderFactoryBean(gemFireCache);
		gatewaySenderFactoryBean.setBatchSize(15);
		gatewaySenderFactoryBean.setBatchTimeInterval(1000);
		gatewaySenderFactoryBean.setRemoteDistributedSystemId(1);
		gatewaySenderFactoryBean.setDiskStoreRef("DiskStore");
		gatewaySenderFactoryBean.setTransportFilters(Collections.singletonList(gatewayTransportFilter));
		gatewaySenderFactoryBean.setPersistent(false);
		return gatewaySenderFactoryBean;
	}
}