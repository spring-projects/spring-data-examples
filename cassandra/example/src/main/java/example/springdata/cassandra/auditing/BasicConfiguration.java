/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.cassandra.auditing;

import java.util.Optional;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.domain.AuditorAware;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * Basic {@link Configuration} to create the necessary schema for the {@link AuditedPerson} table.
 *
 * @author Mark Paluch
 */
@SpringBootApplication
@EntityScan(basePackageClasses = AuditedPerson.class)
@EnableCassandraAuditing
class BasicConfiguration {

	/**
	 * {@code @Bean} method defining a supplier for an auditor. This could be also an integration with a security
	 * framework such as Spring Security.
	 */
	@Bean
	AuditorAware<String> auditorAware() {
		return () -> Optional.of("Some user");
	}

	/**
	 * {@code @Bean} method defining a {@link MappingCassandraConverter} as currently the auditing requires a bean
	 * definition for {@link MappingCassandraConverter}.
	 */
	@Bean
	public MappingCassandraConverter cassandraConverter(CassandraMappingContext mapping,
			CassandraCustomConversions conversions, CqlSession session) {

		var converter = new MappingCassandraConverter(mapping);

		converter.setCodecRegistry(session.getContext().getCodecRegistry());
		converter.setCustomConversions(conversions);
		converter.setUserTypeResolver(new SimpleUserTypeResolver(session));

		return converter;
	}

}
