/*
 * Copyright 2018-2019 the original author or authors.
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

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Configuration
@EnableR2dbcRepositories
class InfrastructureConfiguration extends AbstractR2dbcConfiguration {

	@Bean
	@Override
	public H2ConnectionFactory connectionFactory() {

		H2ConnectionConfiguration config = H2ConnectionConfiguration.builder() //
				.inMemory("test-database2") //
				.option("DB_CLOSE_DELAY=-1") //
				.build();

		return new H2ConnectionFactory(config);
	}
}
