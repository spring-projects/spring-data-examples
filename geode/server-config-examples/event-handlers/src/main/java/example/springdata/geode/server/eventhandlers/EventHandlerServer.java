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

package example.springdata.geode.server.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.LongStream;

@SpringBootApplication(scanBasePackageClasses = EventHandlerServerConfiguration.class)
public class EventHandlerServer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		new SpringApplicationBuilder(EventHandlerServer.class).web(WebApplicationType.NONE).build().run(args);
	}

	@Bean
	ApplicationRunner runner(CustomerRepository customerRepository, ProductRepository productRepository) {
		return args -> {
			createCustomerData(customerRepository);
			createProducts(productRepository);

			final Optional<Product> product = productRepository.findById(5L);
			logger.info("product = " + product.get());
		};
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
		logger.info("Inserting 3 entries for keys: 1, 2, 3");
		LongStream.rangeClosed(1, 3)
				.forEach(customerId ->
						customerRepository.save(new Customer(customerId, new EmailAddress(customerId + "@2.com"), "John" + customerId, "Smith" + customerId)));
	}
}
