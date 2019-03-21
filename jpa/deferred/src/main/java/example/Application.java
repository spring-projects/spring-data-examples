/*
 * Copyright 2018 the original author or authors.
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
package example;

import example.model.Customer;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	/**
	 * Configures a {@link LocalContainerEntityManagerFactoryBean} to use background initialization for the {@code lazy}
	 * and {@code deferred} profiles.
	 *
	 * @author Oliver Gierke
	 */
	@Configuration
	@Profile({ "lazy", "deferred" })
	static class LazyJpaConfiguration {

		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter,
				DataSource dataSource, Environment environment) {

			ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
			threadPoolTaskExecutor.setDaemon(true);
			threadPoolTaskExecutor.afterPropertiesSet();

			LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
			emf.setBootstrapExecutor(threadPoolTaskExecutor);
			emf.setDataSource(dataSource);
			emf.setJpaVendorAdapter(jpaVendorAdapter);
			emf.setPackagesToScan(Customer.class.getPackage().getName());

			return emf;
		}
	}

	/**
	 * Bootstraps Spring Data JPA in lazy mode if the {@code lazy} profile is activated.
	 *
	 * @author Oliver Gierke
	 */
	@Profile("lazy")
	@Configuration
	@EnableJpaRepositories(bootstrapMode = BootstrapMode.LAZY)
	static class LazyRepositoryConfiguration {}

	/**
	 * Bootstraps Spring Data JPA in deferred mode if the {@code deferred} profile is activated.
	 *
	 * @author Oliver Gierke
	 */
	@Profile("deferred")
	@Configuration
	@EnableJpaRepositories(bootstrapMode = BootstrapMode.DEFERRED)
	static class DeferredRepositoryConfiguration {}
}
