/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.jdbc.immutables;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.MappingJdbcConverter;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.conversion.RowDocumentAccessor;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.util.TypeInformation;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.util.ClassUtils;

/**
 * Configuration stub.
 *
 * @author Mark Paluch
 */
@SpringBootApplication
class Application {

	/**
	 * Name scheme how Immutables generates implementations from interface/class definitions.
	 */
	public static final String IMMUTABLE_IMPLEMENTATION_CLASS = "%s.Immutable%s";

	@Configuration
	static class ImmutablesJdbcConfiguration extends AbstractJdbcConfiguration {

		private final ResourceLoader resourceLoader;

		public ImmutablesJdbcConfiguration(ResourceLoader resourceLoader) {
			this.resourceLoader = resourceLoader;
		}

		/**
		 * {@link JdbcConverter} that redirects entities to be instantiated towards the implementation. See
		 * {@link #IMMUTABLE_IMPLEMENTATION_CLASS} and
		 * {@link #getImplementationEntity(JdbcMappingContext, RelationalPersistentEntity)}.
		 *
		 * @param mappingContext
		 * @param operations
		 * @param relationResolver
		 * @param conversions
		 * @param dialect
		 * @return
		 */
		@Override
		public JdbcConverter jdbcConverter(JdbcMappingContext mappingContext, NamedParameterJdbcOperations operations,
				@Lazy RelationResolver relationResolver, JdbcCustomConversions conversions, Dialect dialect) {

			var jdbcTypeFactory = new DefaultJdbcTypeFactory(operations.getJdbcOperations());

			return new MappingJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory) {

				@Override
				@SuppressWarnings("all")
				protected <S> S readAggregate(ConversionContext context, RowDocumentAccessor documentAccessor,
						TypeInformation<? extends S> typeHint) {

					RelationalPersistentEntity<?> implementationEntity = getImplementationEntity(mappingContext,
							mappingContext.getRequiredPersistentEntity(typeHint));

					return (S) super.readAggregate(context, documentAccessor, implementationEntity.getTypeInformation());
				}
			};
		}

		/**
		 * Returns if the entity passed as an argument is an interface the implementation provided by Immutable is provided
		 * instead. In all other cases the entity passed as an argument is returned.
		 */
		@SuppressWarnings("unchecked")
		private <T> RelationalPersistentEntity<T> getImplementationEntity(JdbcMappingContext mappingContext,
				RelationalPersistentEntity<T> entity) {

			Class<T> type = entity.getType();
			if (type.isInterface()) {

				var immutableClass = String.format(IMMUTABLE_IMPLEMENTATION_CLASS, type.getPackageName(), type.getSimpleName());
				if (ClassUtils.isPresent(immutableClass, resourceLoader.getClassLoader())) {

					return (RelationalPersistentEntity<T>) mappingContext
							.getPersistentEntity(ClassUtils.resolveClassName(immutableClass, resourceLoader.getClassLoader()));
				}

			}
			return entity;
		}
	}

}
