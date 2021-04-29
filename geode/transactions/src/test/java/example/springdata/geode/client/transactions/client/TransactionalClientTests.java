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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import example.springdata.geode.client.transactions.Customer;
import example.springdata.geode.client.transactions.EmailAddress;
import example.springdata.geode.client.transactions.server.TransactionalServer;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Patrick Johnson
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionalClientConfig.class)
@CommonsLog
public class TransactionalClientTests extends ForkingClientServerIntegrationTestsSupport {

	@Autowired private CustomerService customerService;

	@Resource(name = "Customers") private Region<Long, Customer> customers;

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(TransactionalServer.class);
	}

	@Test
	public void transactionsConfiguredCorrectly() throws InterruptedException {

		log.info("Number of Entries stored before = " + customerService.numberEntriesStoredOnServer());
		customerService.createFiveCustomers();
		assertThat(customerService.numberEntriesStoredOnServer()).isEqualTo(5);
		log.info("Number of Entries stored after = " + customerService.numberEntriesStoredOnServer());
		log.info("Customer for ID before (transaction commit success) = " + customerService.findById(2L).get());
		customerService.updateCustomersSuccess();
		assertThat(customerService.numberEntriesStoredOnServer()).isEqualTo(5);
		var customer = customerService.findById(2L).get();
		assertThat(customer.getFirstName()).isEqualTo("Humpty");
		log.info("Customer for ID after (transaction commit success) = " + customer);

		assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomersFailure());

		customer = customerService.findById(2L).get();
		assertThat(customer.getFirstName()).isEqualTo("Humpty");
		log.info("Customer for ID after (transaction commit failure) = " + customerService.findById(2L).get());

		var numpty = new Customer(2L, new EmailAddress("2@2.com"), "Numpty", "Hamilton");
		var frumpy = new Customer(2L, new EmailAddress("2@2.com"), "Frumpy", "Hamilton");
		customerService.updateCustomersWithDelay(1000, numpty);
		customerService.updateCustomersWithDelay(10, frumpy);
		customer = customerService.findById(2L).get();
		assertThat(customer).isEqualTo(frumpy);
		log.info("Customer for ID after 2 updates with delay = " + customer);
	}
}
