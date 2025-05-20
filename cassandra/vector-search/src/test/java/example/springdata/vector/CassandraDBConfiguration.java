/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.vector;

import java.net.InetSocketAddress;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraDBConfiguration {

    @Bean
    CqlSessionBuilderCustomizer sessionBuilderCustomizer(CassandraProperties properties) {
        return sessionBuilder -> {

            InetSocketAddress contactPoint = new InetSocketAddress(properties.getContactPoints().iterator().next(), properties.getPort());

            CqlSession session = CqlSession.builder().addContactPoint(contactPoint)
                .withLocalDatacenter(properties.getLocalDatacenter()).build();

            session.execute("CREATE KEYSPACE IF NOT EXISTS " + properties.getKeyspaceName() + " WITH replication = \n"
                + "{'class':'SimpleStrategy','replication_factor':'1'};");
            session.close();
        };
    }
}
