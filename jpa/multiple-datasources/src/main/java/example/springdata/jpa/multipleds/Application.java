/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.multipleds;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import example.springdata.jpa.multipleds.customer.Customer.CustomerId;

/**
 * Core Spring Boot application configuration. Note, that we explicitly deactivate some auto-configurations explicitly.
 * They mostly will even disable automatically if special bean names are used (e.g. {@code entityManagerFactory}) but I
 * wanted to keep the two configurations symmetric. The configuration classes being located in separate packages serves
 * the purpose of scoping the Spring Data repository scanning to those packages so that the infrastructure setup is
 * attached to the corresponding repository instances.
 * 
 * @author Oliver Gierke
 * @see example.springdata.jpa.multipleds.customer.CustomerConfig
 * @see example.springdata.jpa.multipleds.order.OrderConfig
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired DataInitializer initializer;

	@PostConstruct
	public void init() {

		CustomerId customerId = initializer.initializeCustomer();
		initializer.initializeOrder(customerId);
	}
}
