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

package example.springdata.geode.client.basic;

import example.springdata.geode.client.basic.client.BasicClientApplicationConfig;
import example.springdata.geode.client.basic.client.CustomerRepository;
import example.springdata.geode.client.basic.server.Server;
import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.data.gemfire.util.RegionUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BasicClientApplicationConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BasicClientTest extends ForkingClientServerIntegrationTestsSupport {

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
		assertThat(this.customers).isEmpty();
	}

	@Test
	public void repositoryWasAutoConfiguredCorrectly() {
		logger.info("Inserting 3 entries for keys: 1, 2, 3");
		Customer john = new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith");
		Customer frank = new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport");
		Customer jude = new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons");
		customerRepository.save(john);
		customerRepository.save(frank);
		customerRepository.save(jude);

		int localEntries = customers.keySet().size();
		assertThat(localEntries).isEqualTo(0);
		logger.info("Entries on Client: " + localEntries);
		int serverEntries = customers.keySetOnServer().size();
		assertThat(serverEntries).isEqualTo(3);
		logger.info("Entries on Server: " + serverEntries);
		List<Customer> all = customerRepository.findAll();
		assertThat(all.size()).isEqualTo(3);
		all.forEach(customer -> logger.info("\t Entry: \n \t\t " + customer));

		logger.info("Updating entry for key: 2");
		Customer customer = customerRepository.findById(2L).get();
		assertThat(customer).isEqualTo(frank);
		logger.info("Entry Before: " + customer);
		Customer sam = new Customer(2L, new EmailAddress("4@4.com"), "Sam", "Spacey");
		customerRepository.save(sam);
		customer = customerRepository.findById(2L).get();
		assertThat(customer).isEqualTo(sam);
		logger.info("Entry After: " + customer);

		logger.info("Removing entry for key: 3");
		customerRepository.deleteById(3L);
		assertThat(customerRepository.findById(3L)).isEmpty();

		logger.info("Entries:");
		all = customerRepository.findAll();
		assertThat(all.size()).isEqualTo(2);
		all.forEach(c -> logger.info("\t Entry: \n \t\t " + c));
	}
}