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

package example.springdata.geode.client.cq;

import example.springdata.geode.client.cq.client.CQClientApplicationConfig;
import example.springdata.geode.client.cq.client.CustomerRepository;
import example.springdata.geode.client.cq.server.Server;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.query.CqEvent;
import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.data.gemfire.util.RegionUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CQClientApplicationConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CQTest extends ForkingClientServerIntegrationTestsSupport {

	private AtomicInteger counter = new AtomicInteger(0);

	@Autowired
	ContinuousQueryListenerContainer container;

	@Autowired
	private CustomerRepository customerRepository;

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(Server.class);
	}

	@Test
	public void customerRepositoryWasConfiguredCorrectly() {

		assertThat(this.customerRepository).isNotNull();
	}

	@Test
	public void customersRegionWasConfiguredCorrectly() {

		assertThat(this.customers).isNotNull();
		assertThat(this.customers.getName()).isEqualTo("Customers");
		assertThat(this.customers.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Customers"));
	}

	@Test
	public void continuousQueryWorkingCorrectly() {
		assertThat(this.customers).isEmpty();
		logger.info("Inserting 3 entries for keys: 1, 2, 3");
		customerRepository.save(new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith"));
		customerRepository.save(new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport"));
		customerRepository.save(new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons"));
		assertThat(customers.keySetOnServer().size()).isEqualTo(3);

		Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> this.counter.get() == 3);
	}

	@ContinuousQuery(name = "CustomerCQ", query = "SELECT * FROM /Customers")
	public void handleEvent(CqEvent event) {
		logger.info("Received message for CQ 'CustomerCQ'" + event);
		counter.incrementAndGet();
	}

	@After
	public void tearDown() {
		container.getQueryService().closeCqs();
	}
}