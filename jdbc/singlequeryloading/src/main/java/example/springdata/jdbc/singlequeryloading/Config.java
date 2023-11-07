/*
 * Copyright 2023 the original author or authors.
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
package example.springdata.jdbc.singlequeryloading;

import java.util.Optional;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.data.relational.core.mapping.NamingStrategy;

/**
 * Spring application context configuration that enables Single Query Loading.
 *
 * @author Jens Schauder
 */
@SpringBootConfiguration
@EnableJdbcRepositories
public class Config extends AbstractJdbcConfiguration {

	@Override
	public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy,
			JdbcCustomConversions customConversions, RelationalManagedTypes jdbcManagedTypes) {

		JdbcMappingContext jdbcMappingContext = super.jdbcMappingContext(namingStrategy, customConversions,
				jdbcManagedTypes);
		jdbcMappingContext.setSingleQueryLoadingEnabled(true);
		return jdbcMappingContext;
	}
}
