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

package example.springdata.geode.client.function.client;

import example.springdata.geode.client.function.Address;
import example.springdata.geode.client.function.Customer;
import example.springdata.geode.client.function.EmailAddress;
import example.springdata.geode.client.function.LineItem;
import example.springdata.geode.client.function.Order;
import example.springdata.geode.client.function.Product;
import example.springdata.geode.client.function.server.FunctionServer;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = FunctionInvocationClientApplicationConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class FunctionInvocationClientTest extends ForkingClientServerIntegrationTestsSupport {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerFunctionExecutions customerFunctionExecutions;

	@Autowired
	private OrderFunctionExecutions orderFunctionExecutions;

	@Autowired
	private ProductFunctionExecutions productFunctionExecutions;

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	@Resource(name = "Orders")
	private Region<Long, Order> orders;

	@Resource(name = "Products")
	private Region<Long, Product> products;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(FunctionServer.class);
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
	public void ordersRegionWasConfiguredCorrectly() {

		assertThat(this.orders).isNotNull();
		assertThat(this.orders.getName()).isEqualTo("Orders");
		assertThat(this.orders.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Orders"));
		assertThat(this.orders).isEmpty();
	}

	@Test
	public void productsRegionWasConfiguredCorrectly() {

		assertThat(this.products).isNotNull();
		assertThat(this.products.getName()).isEqualTo("Products");
		assertThat(this.products.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Products"));
		assertThat(this.products).isEmpty();
	}

	@Test
	public void orderRepositoryWasConfiguredCorrectly() {

		assertThat(this.orderRepository).isNotNull();
	}

	@Test
	public void productRepositoryWasConfiguredCorrectly() {

		assertThat(this.productRepository).isNotNull();
	}

	@Test
	public void functionsExecuteCorrectly() {
		createCustomerData();

		List<Customer> cust = customerFunctionExecutions.listAllCustomersForEmailAddress("2@2.com", "3@3.com").get(0);
		assertThat(cust.size()).isEqualTo(2);
		logger.info("All customers for emailAddresses:3@3.com,2@2.com using function invocation: \n\t " + cust);

		createProducts();
		BigDecimal sum = productFunctionExecutions.sumPricesForAllProducts().get(0);
		assertThat(sum).isEqualTo(BigDecimal.valueOf(1499.97));
		logger.info("Running function to sum up all product prices: \n\t" + sum);

		createOrders();

		sum = orderFunctionExecutions.sumPricesForAllProductsForOrder(1L).get(0);
		assertThat(sum).isGreaterThanOrEqualTo(BigDecimal.valueOf(99.99));
		logger.info("Running function to sum up all order lineItems prices for order 1: \n\t" + sum);
		Order order = orderRepository.findById(1L).get();
		logger.info("For order: \n\t " + order);
	}

	public void createCustomerData() {

		logger.info("Inserting 3 entries for keys: 1, 2, 3");
		customerRepository.save(new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith"));
		customerRepository.save(new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport"));
		customerRepository.save(new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons"));
		assertThat(customers.keySetOnServer().size()).isEqualTo(3);
	}

	public void createProducts() {
		productRepository.save(new Product(1L, "Apple iPod", new BigDecimal("99.99"),
				"An Apple portable music player"));
		productRepository.save(new Product(2L, "Apple iPad", new BigDecimal("499.99"),
				"An Apple tablet device"));
		Product macbook = new Product(3L, "Apple macBook", new BigDecimal("899.99"),
				"An Apple notebook computer");
		macbook.addAttribute("warranty", "included");
		productRepository.save(macbook);
		assertThat(products.keySetOnServer().size()).isEqualTo(3);
	}

	public void createOrders() {
		Random random = new Random();
		Address address = new Address("it", "doesn't", "matter");
		LongStream.rangeClosed(1, 100).forEach((orderId) ->
				LongStream.rangeClosed(1, 3).forEach((customerId) -> {
					Order order = new Order(orderId, customerId, address);
					IntStream.rangeClosed(0, random.nextInt(3) + 1).forEach((lineItemCount) -> {
						int quantity = random.nextInt(3) + 1;
						long productId = random.nextInt(3) + 1;
						order.add(new LineItem(productRepository.findById(productId).get(), quantity));
					});
					orderRepository.save(order);
				}));
		assertThat(orders.keySetOnServer().size()).isEqualTo(100);
	}
}