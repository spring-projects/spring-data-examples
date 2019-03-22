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
package example.springdata.r2dbc.basics;

import example.springdata.test.util.InfrastructureRule;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.testcontainers.containers.PostgreSQLContainer;
import reactor.test.StepVerifier;

/**
 * @author Oliver Gierke
 */
@Configuration
class InfrastructureConfiguration {

	@Bean
	InfrastructureRule<PostgresqlConnectionConfiguration> infrastructureRule() {

		return new InfrastructureRule<>( //
				this::checkForlLocalPostgres, //
				this::startPostgresInDocker //
		);
	}

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

		return new PostgresqlConnectionFactory(infrastructureRule().getInfo());
	}

	@NotNull
	private InfrastructureRule.InfrastructureInfo<PostgresqlConnectionConfiguration> startPostgresInDocker() {

		PostgreSQLContainer postgres = new PostgreSQLContainer();
		postgres.start();

		PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration.builder() //
				.host(postgres.getContainerIpAddress()) //
				.port(postgres.getFirstMappedPort()) //
				.database(postgres.getDatabaseName()) //
				.username(postgres.getUsername()) //
				.password(postgres.getPassword()) //
				.build();

		return new InfrastructureRule.InfrastructureInfo<>(true, configuration, null, postgres::stop);
	}

	@NotNull
	private InfrastructureRule.InfrastructureInfo<PostgresqlConnectionConfiguration> checkForlLocalPostgres() {

		PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration.builder() //
				.host("localhost") //
				.port(5432) //
				.database("postgres") //
				.username("postgres") //
				.password("") //
				.build();

		try {

			new PostgresqlConnectionFactory(configuration).create()
					.as(StepVerifier::create) //
					.assertNext(c -> {
					}) //
					.verifyComplete();
		} catch (AssertionError re) {

			return new InfrastructureRule.InfrastructureInfo<>(false, null, re, () ->{});
		}

		return new InfrastructureRule.InfrastructureInfo<>(true, configuration, null, () ->{});
	}

}
