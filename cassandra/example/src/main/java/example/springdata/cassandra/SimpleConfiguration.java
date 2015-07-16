/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.cassandra;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cassandra.config.java.AbstractCqlTemplateConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.Session;

/**
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Configuration
@EnableAutoConfiguration
class SimpleConfiguration {

	@Configuration
	@EnableCassandraRepositories
	static class CassandraConfig extends AbstractCqlTemplateConfiguration {

		@Override
		public String getKeyspaceName() {
			return "example";
		}
		
		@Bean
		public CassandraTemplate cassandraTemplate(Session session) {
			return new CassandraTemplate(session);
		}
	}
}
