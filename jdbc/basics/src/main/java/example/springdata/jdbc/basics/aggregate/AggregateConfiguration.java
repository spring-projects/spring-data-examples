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
package example.springdata.jdbc.basics.aggregate;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.DataAccessStrategy;
import org.springframework.data.jdbc.core.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.DelegatingDataAccessStrategy;
import org.springframework.data.jdbc.core.SqlGeneratorSource;
import org.springframework.data.jdbc.mapping.event.BeforeSave;
import org.springframework.data.jdbc.mapping.model.ConversionCustomizer;
import org.springframework.data.jdbc.mapping.model.DefaultNamingStrategy;
import org.springframework.data.jdbc.mapping.model.JdbcMappingContext;
import org.springframework.data.jdbc.mapping.model.JdbcPersistentProperty;
import org.springframework.data.jdbc.mapping.model.NamingStrategy;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;

/**
 * @author Jens Schauder
 */
@Configuration
@EnableJdbcRepositories
public class AggregateConfiguration {

	@Bean
	public ApplicationListener<?> idSetting() {

		final AtomicInteger id = new AtomicInteger(0);

		return (ApplicationListener<BeforeSave>) event -> {

			Object entity = event.getEntity();

			if (entity instanceof LegoSet) {

				LegoSet legoSet = (LegoSet) entity;

				if (legoSet.getId() == 0) {
					legoSet.setId(id.incrementAndGet());
				}

				Manual manual = legoSet.getManual();

				if (manual != null) {
					manual.setId((long) legoSet.getId());
				}
			}
		};
	}

	@Bean
	public NamingStrategy namingStrategy() {

		Map<String, String> tableAliases = new HashMap<String, String>();
		tableAliases.put("Manual", "Handbuch");

		Map<String, String> columnAliases = new HashMap<String, String>();
		columnAliases.put("LegoSet.intMaximumAge", "maxAge");
		columnAliases.put("LegoSet.intMinimumAge", "minAge");
		columnAliases.put("Handbuch.id", "Handbuch_id");

		Map<String, String> reverseColumnAliases = new HashMap<String, String>();
		reverseColumnAliases.put("manual", "Handbuch_id");

		Map<String, String> keyColumnAliases = new HashMap<String, String>();
		keyColumnAliases.put("models", "name");

		return new DefaultNamingStrategy() {

			@Override
			public String getColumnName(JdbcPersistentProperty property) {

				String defaultName = super.getColumnName(property);
				String key = getTableName(property.getOwner().getType()) + "." + defaultName;
				return columnAliases.computeIfAbsent(key, __ -> defaultName);
			}

			@Override
			public String getTableName(Class<?> type) {
				return tableAliases.computeIfAbsent(super.getTableName(type), key -> key);
			}

			@Override
			public String getReverseColumnName(JdbcPersistentProperty property) {
				return reverseColumnAliases.computeIfAbsent(property.getName(), __ -> super.getReverseColumnName(property));
			}

			@Override
			public String getKeyColumn(JdbcPersistentProperty property) {
				return keyColumnAliases.computeIfAbsent(property.getName(), __ -> super.getKeyColumn(property));
			}
		};
	}

	@Bean
	public ConversionCustomizer conversionCustomizer() {

		return conversions -> conversions.addConverter(new Converter<Clob, String>() {

			@Nullable
			@Override
			public String convert(Clob clob) {

				try {

					return Math.toIntExact(clob.length()) == 0 //
							? "" //
							: clob.getSubString(1, Math.toIntExact(clob.length()));

				} catch (SQLException e) {
					throw new IllegalStateException("Failed to convert CLOB to String.", e);
				}
			}
		});
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
