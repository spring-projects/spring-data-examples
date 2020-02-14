/*
 * Copyright 2020 the original author or authors.
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

package example.springdata.geode.server.wan.event;

import example.springdata.geode.server.wan.event.client.WanClientConfig;
import example.springdata.geode.server.wan.event.server.WanServer;
import org.apache.geode.cache.Region;
import org.awaitility.Awaitility;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = WanClientConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class WanServerTests extends ForkingClientServerIntegrationTestsSupport {

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(WanServer.class, "-Dspring.profiles.active=SiteB");
		startGemFireServer(WanServer.class, "-Dspring.profiles.active=SiteA");
		System.getProperties().remove("spring.data.gemfire.pool.servers");
	}

	@Test
	@Ignore
	public void wanReplicationOccursCorrectly() {
		Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> customers.keySetOnServer().size() == 150);
		assertThat(customers.keySetOnServer().size()).isEqualTo(150);
		logger.info(customers.keySetOnServer().size() + " entries replicated to siteA");

		customers.getAll(customers.keySetOnServer()).forEach((key, value) -> assertThat(value.getLastName().length()).isEqualTo(1));
		logger.info("All customers' last names changed to last initial on siteA");
	}
}
