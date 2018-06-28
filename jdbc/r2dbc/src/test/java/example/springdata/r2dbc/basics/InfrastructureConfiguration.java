/*
 * Copyright 2018 the original author or authors.
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
package example.springdata.r2dbc.basics;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;

/**
 * @author Oliver Gierke
 */
@Configuration
class InfrastructureConfiguration {

	@Bean
	CustomerRepository customerRepository(R2dbcRepositoryFactory factory) {
		return factory.getRepository(CustomerRepository.class);
	}

	@Bean
	R2dbcRepositoryFactory repositoryFactory(DatabaseClient client) {

		RelationalMappingContext context = new RelationalMappingContext();
		context.afterPropertiesSet();

		return new R2dbcRepositoryFactory(client, context);
	}

	@Bean
	DatabaseClient databaseClient(ConnectionFactory factory) {

		return DatabaseClient.builder() //
				.connectionFactory(factory) //
				.build();
	}

	@Bean
	PostgresqlConnectionFactory connectionFactory() {

		PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder() //
				.host("localhost") //
				.port(5432) //
				.database("postgres") //
				.username("postgres") //
				.password("") //
				.build();

		return new PostgresqlConnectionFactory(config);
	}
}
