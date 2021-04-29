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
package example.springdata.geode.client.function.client;

import static org.assertj.core.api.Assertions.*;

import example.springdata.geode.client.function.Address;
import example.springdata.geode.client.function.Customer;
import example.springdata.geode.client.function.EmailAddress;
import example.springdata.geode.client.function.LineItem;
import example.springdata.geode.client.function.Order;
import example.springdata.geode.client.function.Product;
import example.springdata.geode.client.function.server.FunctionServer;
import lombok.extern.apachecommons.CommonsLog;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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
@SpringBootTest(classes = FunctionInvocationClientApplicationConfig.class)
@CommonsLog
public class FunctionInvocationClientTests extends ForkingClientServerIntegrationTestsSupport {

	@Autowired private CustomerRepository customerRepository;

	@Autowired private OrderRepository orderRepository;

	@Autowired private ProductRepository productRepository;

	@Autowired private CustomerFunctionExecutions customerFunctionExecutions;

	@Autowired private OrderFunctionExecutions orderFunctionExecutions;

	@Autowired private ProductFunctionExecutions productFunctionExecutions;

	@Resource(name = "Customers") private Region<Long, Customer> customers;

	@Resource(name = "Orders") private Region<Long, Order> orders;

	@Resource(name = "Products") private Region<Long, Product> products;

	@BeforeClass
	public static void setup() throws IOException {
		startGemFireServer(FunctionServer.class);
	}

	@Test
	public void functionsExecuteCorrectly() {
		createCustomerData();

		var cust = customerFunctionExecutions.listAllCustomersForEmailAddress("2@2.com", "3@3.com").get(0);
		assertThat(cust.size()).isEqualTo(2);
		log.info("All customers for emailAddresses:3@3.com,2@2.com using function invocation: \n\t " + cust);

		createProducts();
		var sum = productFunctionExecutions.sumPricesForAllProducts().get(0);
		assertThat(sum).isEqualTo(BigDecimal.valueOf(1499.97));
		log.info("Running function to sum up all product prices: \n\t" + sum);

		createOrders();

		sum = orderFunctionExecutions.sumPricesForAllProductsForOrder(1L).get(0);
		assertThat(sum).isGreaterThanOrEqualTo(BigDecimal.valueOf(99.99));
		log.info("Running function to sum up all order lineItems prices for order 1: \n\t" + sum);
		var order = orderRepository.findById(1L).get();
		log.info("For order: \n\t " + order);
	}

	public void createCustomerData() {

		log.info("Inserting 3 entries for keys: 1, 2, 3");

		customerRepository.save(new Customer(1L, new EmailAddress("2@2.com"), "John", "Smith"));
		customerRepository.save(new Customer(2L, new EmailAddress("3@3.com"), "Frank", "Lamport"));
		customerRepository.save(new Customer(3L, new EmailAddress("5@5.com"), "Jude", "Simmons"));

		assertThat(customers.keySetOnServer().size()).isEqualTo(3);
	}

	public void createProducts() {

		productRepository.save(new Product(1L, "Apple iPod", new BigDecimal("99.99"), "An Apple portable music player"));
		productRepository.save(new Product(2L, "Apple iPad", new BigDecimal("499.99"), "An Apple tablet device"));
		var macbook = new Product(3L, "Apple macBook", new BigDecimal("899.99"), "An Apple notebook computer");
		macbook.addAttribute("warranty", "included");

		productRepository.save(macbook);

		assertThat(products.keySetOnServer().size()).isEqualTo(3);
	}

	public void createOrders() {

		var random = new Random();
		var address = new Address("it", "doesn't", "matter");

		LongStream.rangeClosed(1, 100).forEach((orderId) -> LongStream.rangeClosed(1, 3).forEach((customerId) -> {
			var order = new Order(orderId, customerId, address);
			IntStream.rangeClosed(0, random.nextInt(3) + 1).forEach((lineItemCount) -> {
				var quantity = random.nextInt(3) + 1;
				long productId = random.nextInt(3) + 1;
				order.add(new LineItem(productRepository.findById(productId).get(), quantity));
			});
			orderRepository.save(order);
		}));

		assertThat(orders.keySetOnServer().size()).isEqualTo(100);
	}
}
