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
package example.springdata.geode.server.events;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.geode.cache.Region;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author Patrick Johnson
 */
@SpringBootApplication
@CommonsLog
public class EventServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EventServer.class).web(WebApplicationType.NONE).build().run(args);
	}

	@Bean
	public ApplicationRunner runner(CustomerRepository customerRepository, OrderRepository orderRepository,
			ProductRepository productRepository, OrderProductSummaryRepository orderProductSummaryRepository,
			@Qualifier("Products") Region<Long, Product> products) {
		return args -> {
			createCustomerData(customerRepository);

			createProducts(productRepository);

			createOrders(productRepository, orderRepository);

			log.info("Completed creating orders ");

			var allForProductID = orderProductSummaryRepository.findAllForProductID(3L);
			allForProductID.forEach(orderProductSummary -> log.info("orderProductSummary = " + orderProductSummary));
		};
	}

	private void createOrders(ProductRepository productRepository, OrderRepository orderRepository) {
		var random = new Random(System.nanoTime());
		var address = new Address("it", "doesn't", "matter");
		LongStream.rangeClosed(1, 10).forEach((orderId) -> LongStream.rangeClosed(1, 300).forEach((customerId) -> {
			var order = new Order(orderId, customerId, address);
			IntStream.rangeClosed(0, random.nextInt(3) + 1).forEach((lineItemCount) -> {
				var quantity = random.nextInt(3) + 1;
				long productId = random.nextInt(3) + 1;
				order.add(new LineItem(productRepository.findById(productId).get(), quantity));
			});
			orderRepository.save(order);
		}));
	}

	private void createProducts(ProductRepository productRepository) {
		productRepository.save(new Product(1L, "Apple iPod", new BigDecimal("99.99"), "An Apple portable music player"));
		productRepository.save(new Product(2L, "Apple iPad", new BigDecimal("499.99"), "An Apple tablet device"));
		var macbook = new Product(3L, "Apple macBook", new BigDecimal("899.99"), "An Apple notebook computer");
		macbook.addAttribute("warranty", "included");
		productRepository.save(macbook);
	}

	private void createCustomerData(CustomerRepository customerRepository) {
		LongStream.rangeClosed(0, 300).parallel().forEach(customerId -> customerRepository.save(
				new Customer(customerId, new EmailAddress(customerId + "@2.com"), "John" + customerId, "Smith" + customerId)));
	}
}
