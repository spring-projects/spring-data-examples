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

package example.springdata.geode.server.wan.server.siteA;

import com.github.javafaker.Faker;
import example.springdata.geode.server.wan.Customer;
import example.springdata.geode.server.wan.CustomerRepository;
import example.springdata.geode.server.wan.EmailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.stream.LongStream;

@SpringBootApplication(scanBasePackageClasses = SiteAWanEnabledServerConfig.class)
public class WanEnabledServerSiteA {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		new SpringApplicationBuilder(WanEnabledServerSiteA.class)
				.web(WebApplicationType.NONE)
				.build()
				.run(args);
	}

	@Bean
	public ApplicationRunner siteARunner(CustomerRepository customerRepository) {
		return args -> {
			createCustomerData(customerRepository);
			new Scanner(System.in).nextLine();
		};
	}

	private void createCustomerData(CustomerRepository customerRepository) {
		logger.info("Inserting 301 entries on siteA");
		Faker faker = new Faker();
		LongStream.rangeClosed(0, 300)
				.forEach(customerId ->
						customerRepository.save(
								new Customer(customerId,
										new EmailAddress(faker.internet().emailAddress()), faker.name().firstName(), faker.name().lastName())));
	}
}