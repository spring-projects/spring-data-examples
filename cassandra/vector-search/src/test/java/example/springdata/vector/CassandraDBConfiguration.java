/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.vector;

import java.net.InetSocketAddress;

import org.springframework.boot.cassandra.autoconfigure.CassandraConnectionDetails;
import org.springframework.boot.cassandra.autoconfigure.CassandraProperties;
import org.springframework.boot.cassandra.autoconfigure.CqlSessionBuilderCustomizer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

import com.datastax.oss.driver.api.core.CqlSession;

@Configuration
public class CassandraDBConfiguration {

	@Bean
	@ServiceConnection
	CassandraContainer pgVectorContainer() {
		return new CassandraContainer(DockerImageName.parse("cassandra:5")).withReuse(true);
	}

	@Bean
	CqlSessionBuilderCustomizer sessionBuilderCustomizer(CassandraConnectionDetails connectionDetails,
			CassandraProperties properties) {

		return sessionBuilder -> {

			CqlSession session = CqlSession.builder()
					.addContactPoints(connectionDetails.getContactPoints().stream()
							.map(it -> new InetSocketAddress(it.host(), it.port())).toList())
					.withLocalDatacenter(connectionDetails.getLocalDatacenter()).build();

			session.execute("CREATE KEYSPACE IF NOT EXISTS " + properties.getKeyspaceName() + " WITH replication = \n"
					+ "{'class':'SimpleStrategy','replication_factor':'1'};");
			session.close();
		};
	}

}
