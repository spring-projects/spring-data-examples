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
package example.springdata.geode.client.queries;

import static org.assertj.core.api.Assertions.*;

import example.springdata.geode.client.queries.client.CustomerRepository;
import example.springdata.geode.client.queries.client.QueryClientConfig;
import example.springdata.geode.client.queries.server.QueryServer;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.lucene.LuceneResultStruct;
import org.apache.geode.cache.query.CqEvent;
import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.data.gemfire.search.lucene.LuceneTemplate;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Patrick Johnson
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueryClientConfig.class)
@CommonsLog
public class QueryTests extends ForkingClientServerIntegrationTestsSupport {

	@Autowired private ContinuousQueryListenerContainer container;

	@Autowired private CustomerRepository customerRepository;

	@Autowired private GemfireTemplate customerTemplate;

	@Resource(name = "Customers") private Region<Long, Customer> customers;

	@Autowired private LuceneTemplate luceneTemplate;

	private AtomicInteger counter = new AtomicInteger(0);

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(QueryServer.class);
	}

	@Test
	public void luceneIsConfiguredCorrectly() {

		customerRepository.save(new Customer(1L, new EmailAddress("name@internet.com"), "Stephanie", "Demarco"));
		customerRepository.save(new Customer(2L, new EmailAddress("cool_Guy57@mail.com"), "Patrick", "Dunham"));
		customerRepository.save(new Customer(3L, new EmailAddress("scientist@mail.com"), "Jasmine", "Oliander"));
		customerRepository.save(new Customer(4L, new EmailAddress("catlover42@mail.com"), "Erica", "Shu"));
		customerRepository.save(new Customer(5L, new EmailAddress("zolander@mail.com"), "Tom", "Darude"));

		List<LuceneResultStruct<Long, Customer>> lastName = luceneTemplate.query("D*", "lastName", 10);

		assertThat(lastName.size()).isEqualTo(3);
		log.info("Customers with last names beginning with 'D':");
		lastName.forEach(result -> log.info(result.getValue().toString()));
	}

	@Test
	public void oqlQueriesConfiguredCorrectly() {

		log.info("Inserting 3 entries for keys: 1, 2, 3");
		var john = new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith");
		var frank = new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport");
		var jude = new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons");
		customerRepository.save(john);
		customerRepository.save(frank);
		customerRepository.save(jude);
		assertThat(customers.keySetOnServer().size()).isEqualTo(3);

		var customer = customerRepository.findById(2L).get();
		assertThat(customer).isEqualTo(frank);
		log.info("Find customer with key=2 using GemFireRepository: " + customer);
		List customerList = customerTemplate.find("select * from /Customers where id=$1", 2L).asList();
		assertThat(customerList.size()).isEqualTo(1);
		assertThat(customerList.contains(frank)).isTrue();
		log.info("Find customer with key=2 using GemFireTemplate: " + customerList);

		customer = new Customer(1L, new EmailAddress("3@3.com"), "Jude", "Smith");
		customerRepository.save(customer);
		assertThat(customers.keySetOnServer().size()).isEqualTo(3);

		customerList = customerRepository.findByEmailAddressUsingIndex("3@3.com");
		assertThat(customerList.size()).isEqualTo(2);
		assertThat(customerList.contains(frank)).isTrue();
		assertThat(customerList.contains(customer)).isTrue();
		log.info("Find customers with emailAddress=3@3.com: " + customerList);

		customerList = customerRepository.findByFirstNameUsingIndex("Frank");
		assertThat(customerList.get(0)).isEqualTo(frank);

		log.info("Find customers with firstName=Frank: " + customerList);

		customerList = customerRepository.findByFirstNameUsingIndex("Jude");

		assertThat(customerList.size()).isEqualTo(2);
		assertThat(customerList.contains(jude)).isTrue();
		assertThat(customerList.contains(customer)).isTrue();

		log.info("Find customers with firstName=Jude: " + customerList);
	}

	@Test
	public void continuousQueryWorkingCorrectly() {

		assertThat(this.customers).isEmpty();

		log.info("Inserting 3 entries for keys: 1, 2, 3");

		customerRepository.save(new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith"));
		customerRepository.save(new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport"));
		customerRepository.save(new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons"));

		assertThat(customers.keySetOnServer().size()).isEqualTo(3);

		Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> this.counter.get() == 3);
	}

	@ContinuousQuery(name = "CustomerCQ", query = "SELECT * FROM /Customers")
	public void handleEvent(CqEvent event) {
		log.info("Received message for CQ 'CustomerCQ'" + event);
		counter.incrementAndGet();
	}

	@After
	public void cleanup() {
		customerRepository.deleteAll(customerRepository.findAll());
		container.getQueryService().closeCqs();
	}
}
