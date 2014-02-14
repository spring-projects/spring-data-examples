/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.jpa.auditing;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author Oliver Gierke
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaAuditing
class AuditingConfiguration {

	/**
	 * We need to configure a {@link LocalContainerEntityManagerFactoryBean} manually here as Spring does <em>not</em>
	 * automatically add the {@code orm.xml} <em>if</em> a {@code persistence.xml} is located right beside it. This is
	 * necessary to get the {@link example.springdata.jpa.basics.BasicFactorySetup} sample working. However, in a {code
	 * persistence.xml}-less codebase you can rely on Spring Boot on setting the correct defaults.
	 * 
	 * @return
	 */
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.HSQL);
		adapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan(getClass().getPackage().getName());
		factoryBean.setMappingResources("META-INF/orm.xml");
		factoryBean.setJpaVendorAdapter(adapter);
		factoryBean.setDataSource(dataSource);

		return factoryBean;
	}

	@Bean
	AuditorAwareImpl auditorAware() {
		return new AuditorAwareImpl();
	}
}
