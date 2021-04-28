/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.jdbc.jooq;

import javax.sql.DataSource;

import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 * Contains infrastructure necessary for creating repositories and two listeners.
 * <p>
 * Not that a listener may change an entity without any problem.
 *
 * @author Jens Schauder
 * @author Mark Paluch
 * @author Florian Lüdiger
 */
@Configuration
@EnableJdbcRepositories
public class CategoryConfiguration extends AbstractJdbcConfiguration {

	@Autowired private DataSource dataSource;

	@Bean
	public DataSourceConnectionProvider connectionProvider() {
		return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
	}

	@Bean
	DefaultDSLContext dsl() {
		return new DefaultDSLContext(configuration());
	}

	public DefaultConfiguration configuration() {
		var jooqConfiguration = new DefaultConfiguration();
		jooqConfiguration.set(connectionProvider());
		return jooqConfiguration;
	}
}
