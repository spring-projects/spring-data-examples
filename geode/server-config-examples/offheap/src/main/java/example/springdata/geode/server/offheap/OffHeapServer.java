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

package example.springdata.geode.server.offheap;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.stream.LongStream;

@SpringBootApplication(scanBasePackageClasses = OffHeapServerConfig.class)
public class OffHeapServer {
	public static void main(String[] args) {
		new SpringApplicationBuilder(OffHeapServer.class).web(WebApplicationType.NONE).build().run(args);
	}

	@Bean
	public ApplicationRunner runner(CustomerRepository customerRepository, ProductRepository productRepository) {
		return args -> {
			createCustomers(customerRepository);
			createProducts(productRepository);
		};
	}

	private void createCustomers(CustomerRepository repository) {
		Faker faker = new Faker();
		Name fakerName = faker.name();
		Internet fakerInternet = faker.internet();
		LongStream.range(0, 3000).forEach(index ->
				repository.save(new Customer(index,
						new EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName())));
	}

	private void createProducts(ProductRepository productRepository) {
		Faker faker = new Faker();
		Commerce fakerCommerce = faker.commerce();
		LongStream.range(0, 1000)
				.parallel()
				.forEach(id ->
						productRepository.save(new Product(id, fakerCommerce.productName(), new BigDecimal(fakerCommerce.price(0.01, 100000.0)), "")));
	}
}
