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

package example.springdata.geode.server.wan.event.server;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import example.springdata.geode.server.wan.event.Customer;
import example.springdata.geode.server.wan.event.CustomerRepository;
import example.springdata.geode.server.wan.event.EmailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Scanner;
import java.util.stream.LongStream;

@SpringBootApplication(scanBasePackageClasses = WanServerConfig.class)
public class WanServer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		new SpringApplicationBuilder(WanServer.class)
				.web(WebApplicationType.NONE)
				.build()
				.run(args);
	}

	@Bean
	@Profile({"default", "SiteA"})
	public ApplicationRunner siteARunner() {
		return args -> new Scanner(System.in).nextLine();
	}

	@Bean
	@Profile("SiteB")
	public ApplicationRunner siteBRunner(CustomerRepository customerRepository) {
		return args -> {
			logger.info("Inserting 300 customers");
			createCustomers(customerRepository);
		};
	}

	private void createCustomers(CustomerRepository repository) {
		Faker faker = new Faker();
		Name fakerName = faker.name();
		Internet fakerInternet = faker.internet();
		LongStream.range(0, 300).forEach(index ->
				repository.save(new Customer(index,
						new EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName())));
	}
}
