/*
 * Copyright 2015-2018 the original author or authors.
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
package example.springdata.jdbc.multipleds.customer;

import javax.sql.DataSource;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration for the {@link Customer} slice of the system. A dedicated {@link DataSource},
 * {@link DataSourceTransactionManager} and {@link JdbcTemplate}. Note that there could of course be some deduplication
 * with {@link example.springdata.jdbc.multipleds.order.OrderConfig}. I just decided to keep it to focus on the separation of the two. Also, some overlaps might
 * not even occur in real world scenarios (whether to create DDL or the like).
 *
 * @author Jens Schauder
 */
@Configuration
class CustomerConfig extends AbstractJdbcConfiguration {

	@Bean
	PlatformTransactionManager customerTransactionManager() {
		return new DataSourceTransactionManager(customerDataSource());
	}

	@Bean
	NamedParameterJdbcTemplate customerNamedParameterTemplate() {
		return new NamedParameterJdbcTemplate(customerDataSource());
	}

	@Bean
	DataSource customerDataSource() {

		return new EmbeddedDatabaseBuilder().//
				setType(EmbeddedDatabaseType.HSQL).//
				setName("customers").//
				build();
	}

	@Bean
	JdbcRepositoryFactory repositoryFactory(DataAccessStrategy dataAccessStrategy, RelationalMappingContext mappingContext, JdbcConverter converter, ApplicationEventPublisher publisher, NamedParameterJdbcOperations template) {
		return new JdbcRepositoryFactory(dataAccessStrategy, mappingContext, converter, publisher, template);
	}

}
