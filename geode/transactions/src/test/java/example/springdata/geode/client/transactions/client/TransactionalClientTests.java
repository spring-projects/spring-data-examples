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

package example.springdata.geode.client.transactions.client;

import example.springdata.geode.client.transactions.Customer;
import example.springdata.geode.client.transactions.EmailAddress;
import example.springdata.geode.client.transactions.server.TransactionalServer;
import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TransactionalClientConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TransactionalClientTests extends ForkingClientServerIntegrationTestsSupport {

	@Autowired
	private CustomerService customerService;

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(TransactionalServer.class);
	}

	@Test
	public void transactionsConfiguredCorrectly() throws InterruptedException {

		logger.info("Number of Entries stored before = " + customerService.numberEntriesStoredOnServer());
		customerService.createFiveCustomers();
		assertThat(customerService.numberEntriesStoredOnServer()).isEqualTo(5);
		logger.info("Number of Entries stored after = " + customerService.numberEntriesStoredOnServer());
		logger.info("Customer for ID before (transaction commit success) = " + customerService.findById(2L).get());
		customerService.updateCustomersSuccess();
		assertThat(customerService.numberEntriesStoredOnServer()).isEqualTo(5);
		Customer customer = customerService.findById(2L).get();
		assertThat(customer.getFirstName()).isEqualTo("Humpty");
		logger.info("Customer for ID after (transaction commit success) = " + customer);

		try {
			customerService.updateCustomersFailure();
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		}

		customer = customerService.findById(2L).get();
		assertThat(customer.getFirstName()).isEqualTo("Humpty");
		logger.info("Customer for ID after (transaction commit failure) = " + customerService.findById(2L).get());

		Customer numpty = new Customer(2L, new EmailAddress("2@2.com"), "Numpty", "Hamilton");
		Customer frumpy = new Customer(2L, new EmailAddress("2@2.com"), "Frumpy", "Hamilton");
		customerService.updateCustomersWithDelay(1000, numpty);
		customerService.updateCustomersWithDelay(10, frumpy);
		customer = customerService.findById(2L).get();
		assertThat(customer).isEqualTo(frumpy);
		logger.info("Customer for ID after 2 updates with delay = " + customer);
	}
}