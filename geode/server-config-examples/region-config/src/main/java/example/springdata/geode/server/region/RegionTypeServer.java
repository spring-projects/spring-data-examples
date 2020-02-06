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

package example.springdata.geode.server.region;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootApplication(scanBasePackageClasses = RegionTypeConfiguration.class)
public class RegionTypeServer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		new SpringApplicationBuilder(RegionTypeServer.class)
				.web(WebApplicationType.NONE)
				.build()
				.run(args);
	}

	@Bean
	public ApplicationRunner runner(CustomerRepository customerRepository, OrderRepository orderRepository,
									ProductRepository productRepository) {
		return args -> {
			createCustomerData(customerRepository);

			createProducts(productRepository);

			createOrders(productRepository, orderRepository);

			logger.info("There are " + customerRepository.count() + " customers");
			logger.info("There are " + productRepository.count() + " products");
			logger.info("There are " + orderRepository.count() + " orders");
		};
	}

	private void createOrders(ProductRepository productRepository, OrderRepository orderRepository) {
		Random random = new Random(System.nanoTime());
		Address address = new Address("it", "doesn't", "matter");
		LongStream.rangeClosed(1, 100).forEach((orderId) ->
				LongStream.rangeClosed(1, 3000).forEach((customerId) -> {
					Order order = new Order(orderId, customerId, address);
					IntStream.rangeClosed(0, random.nextInt(3) + 1).forEach((lineItemCount) -> {
						int quantity = random.nextInt(3) + 1;
						long productId = random.nextInt(3) + 1;
						order.add(new LineItem(productRepository.findById(productId).get(), quantity));
					});
					orderRepository.save(order);
				}));
	}

	private void createProducts(ProductRepository productRepository) {
		productRepository.save(new Product(1L, "Apple iPod", new BigDecimal("99.99"),
				"An Apple portable music player"));
		productRepository.save(new Product(2L, "Apple iPad", new BigDecimal("499.99"),
				"An Apple tablet device"));
		Product macbook = new Product(3L, "Apple macBook", new BigDecimal("899.99"),
				"An Apple notebook computer");
		macbook.addAttribute("warranty", "included");
		productRepository.save(macbook);
	}

	private void createCustomerData(CustomerRepository customerRepository) {
		LongStream.rangeClosed(0, 3000)
				.parallel()
				.forEach(customerId ->
						customerRepository.save(new Customer(customerId, new EmailAddress(customerId + "@2.com"),
								"John" + customerId, "Smith" + customerId)));
	}
}
