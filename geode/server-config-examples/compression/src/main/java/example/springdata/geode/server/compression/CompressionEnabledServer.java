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

package example.springdata.geode.server.compression;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackageClasses = CompressionEnabledServerConfig.class)
public class CompressionEnabledServer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		new SpringApplicationBuilder(CompressionEnabledServer.class).web(WebApplicationType.NONE).build().run(args);
	}

	@Bean
	ApplicationRunner runner(CustomerRepository customerRepo) {
		logger.info("Inserting 4000 Customers into compressed region");
		return args -> {
			Faker faker = new Faker();
			Name fakerName = faker.name();
			Internet fakerInternet = faker.internet();
			for (long i = 0; i < 4000; i++) {
				customerRepo.save(new Customer(i, new EmailAddress(fakerInternet.emailAddress()), fakerName.firstName(), fakerName.lastName()));
			}
		};
	}
}
