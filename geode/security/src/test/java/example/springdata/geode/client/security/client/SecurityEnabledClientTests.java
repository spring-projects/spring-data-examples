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

package example.springdata.geode.client.security.client;

import example.springdata.geode.client.security.Customer;
import example.springdata.geode.client.security.EmailAddress;
import example.springdata.geode.client.security.server.SecurityEnabledServer;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = SecurityEnabledClientConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SecurityEnabledClientTests extends ForkingClientServerIntegrationTestsSupport {

	@Autowired
	private CustomerRepository customerRepository;

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(SecurityEnabledServer.class);
	}

	@Test
	public void SecurityWasConfiguredCorrectly() {
		logger.info("Inserting 3 entries for keys: 1, 2, 3");
		Customer john = new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith");
		Customer frank = new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport");
		Customer jude = new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons");
		customerRepository.save(john);
		customerRepository.save(frank);
		customerRepository.save(jude);
		assertThat(customers.keySetOnServer().size()).isEqualTo(3);
		logger.info("Customers saved on server:");
		List<Customer> customerList = customerRepository.findAll();
		assertThat(customerList.size()).isEqualTo(3);
		assertThat(customerList.contains(john)).isTrue();
		assertThat(customerList.contains(frank)).isTrue();
		assertThat(customerList.contains(jude)).isTrue();
		customerList.forEach(customer -> logger.info("\t Entry: \n \t\t " + customer));
	}
}