/*
 * Copyright 2017-2018 the original author or authors.
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
package example.springdata.jdbc.basics.simpleentity;

import javax.sql.DataSource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.core.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.DelegatingDataAccessStrategy;
import org.springframework.data.jdbc.core.SqlGeneratorSource;
import org.springframework.data.jdbc.mapping.event.BeforeSave;
import org.springframework.data.jdbc.mapping.event.JdbcEvent;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Contains infrastructure necessary for creating repositories and two listeners.
 * <p>
 * Not that a listener may change an entity without any problem.
 *
 * @author Jens Schauder
 */
@Configuration
@EnableJdbcRepositories
public class CategoryConfiguration {

	@Bean
	public ApplicationListener<?> loggingListener() {

		return (ApplicationListener<ApplicationEvent>) event -> {
			if (event instanceof JdbcEvent) {
				System.out.println("Received an event: " + event);
			}
		};
	}

	@Bean
	public ApplicationListener<BeforeSave> timeStampingSaveTime() {

		return event -> {

			Object entity = event.getEntity();

			if (entity instanceof Category) {
				Category category = (Category) entity;
				category.timeStamp();
			}
		};
	}

	// temporary workaround for https://jira.spring.io/browse/DATAJDBC-155
	@Bean
	DataAccessStrategy defaultDataAccessStrategy(JdbcMappingContext context, DataSource dataSource) {

		NamedParameterJdbcOperations operations = new NamedParameterJdbcTemplate(dataSource);
		DelegatingDataAccessStrategy accessStrategy = new DelegatingDataAccessStrategy();

		accessStrategy.setDelegate(new DefaultDataAccessStrategy( //
				new SqlGeneratorSource(context), //
				operations, //
				context, //
				accessStrategy) //
		);

		return accessStrategy;
	}
}
