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

package example.springdata.geode.functions.cascading.client;

import example.springdata.geode.functions.cascading.Address;
import example.springdata.geode.functions.cascading.Customer;
import example.springdata.geode.functions.cascading.EmailAddress;
import example.springdata.geode.functions.cascading.LineItem;
import example.springdata.geode.functions.cascading.Order;
import example.springdata.geode.functions.cascading.Product;
import example.springdata.geode.functions.cascading.server.CascadingFunctionServer;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CascadingFunctionClientConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CascadingFunctionClientTest extends ForkingClientServerIntegrationTestsSupport {
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

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	@Resource(name = "Orders")
	private Region<Long, Order> orders;

	@Resource(name = "Products")
	private Region<Long, Product> products;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(CascadingFunctionServer.class);
	}

	@Test
	public void customersRegionWasConfiguredCorrectly() {
		assertThat(this.customers).isNotNull();
		assertThat(this.customers.getName()).isEqualTo("Customers");
		assertThat(this.customers.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Customers"));
	}

	@Test
	public void ordersRegionWasConfiguredCorrectly() {
		assertThat(this.orders).isNotNull();
		assertThat(this.orders.getName()).isEqualTo("Orders");
		assertThat(this.orders.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Orders"));
	}

	@Test
	public void productsRegionWasConfiguredCorrectly() {

		assertThat(this.products).isNotNull();
		assertThat(this.products.getName()).isEqualTo("Products");
		assertThat(this.products.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Products"));
	}

	@Test
	public void functionsExecuteCorrectly() {
		IntStream.rangeClosed(1, 10000).parallel().forEach(customerId ->
				customerRepository.save(new Customer(Integer.toUnsignedLong(customerId), new EmailAddress("2@2.com"), "John" + customerId, "Smith" + customerId)));

		assertThat(customers.keySetOnServer().size()).isEqualTo(10000);

		productRepository.save(new Product(1L, "Apple iPod", new BigDecimal("99.99"), "An Apple portable music player"));
		productRepository.save(new Product(2L, "Apple iPad", new BigDecimal("499.99"), "An Apple tablet device"));

		Product product = new Product(3L, "Apple macBook", new BigDecimal("899.99"), "An Apple notebook computer");
		product.addAttribute("warranty", "included");

		productRepository.save(product);

		assertThat(products.keySetOnServer().size()).isEqualTo(3);

		Random random = new Random(System.nanoTime());
		Address address = new Address("it", "doesn't", "matter");

		IntStream.rangeClosed(1, 10).forEach(orderId ->
				LongStream.rangeClosed(1, 10).forEach(customerId -> {
					Order order = new Order(Integer.toUnsignedLong(orderId), customerId, address);
					IntStream.rangeClosed(1, random.nextInt(3) + 1).forEach(i -> {
						int quantity = random.nextInt(3) + 1;
						Long productId = (long) (random.nextInt(3) + 1);
						order.add(new LineItem(productRepository.findById(productId).get(), quantity));
					});
					orderRepository.save(order);
				}));

		assertThat(orders.keySetOnServer().size()).isEqualTo(10);

		List<Long> listAllCustomers = customerFunctionExecutions.listAllCustomers().get(0);
		assertThat(listAllCustomers.size()).isEqualTo(10000);
		logger.info("Number of customers retrieved from servers: " + listAllCustomers.size());

		List<Order> findOrdersForCustomer = orderFunctionExecutions.findOrdersForCustomers(listAllCustomers);
		assertThat(findOrdersForCustomer.size()).isEqualTo(10);
		logger.info(findOrdersForCustomer.toString());
	}
}