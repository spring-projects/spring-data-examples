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
package example.springdata.jpa.multipleds.order;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration for the {@link Order} slice of the system. A dedicated {@link DataSource},
 * {@link JpaTransactionManager} and {@link EntityManagerFactory}. Note that there could of course be some deduplication
 * with {@link example.springdata.jpa.multipleds.customer.CustomerConfig}. I just decided to keep it to focus on the
 * sepeartion of the two. Also, some overlaps might not even occur in real world scenarios (whether to create DDl or the
 * like).
 *
 * @author Oliver Gierke
 */
@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "orderEntityManagerFactory",
		transactionManagerRef = "orderTransactionManager")
class OrderConfig {

	@Bean
	PlatformTransactionManager orderTransactionManager() {
		return new JpaTransactionManager(orderEntityManagerFactory().getObject());
	}

	@Bean
	LocalContainerEntityManagerFactoryBean orderEntityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		factoryBean.setDataSource(orderDataSource());
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setPackagesToScan(OrderConfig.class.getPackage().getName());

		return factoryBean;
	}

	@Bean
	DataSource orderDataSource() {

		return new EmbeddedDatabaseBuilder().//
				setType(EmbeddedDatabaseType.HSQL).//
				setName("orders").//
				build();
	}
}
