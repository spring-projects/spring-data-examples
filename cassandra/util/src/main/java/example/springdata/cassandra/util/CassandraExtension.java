/*
 * Copyright 2021-2021 the original author or authors.
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
package example.springdata.cassandra.util;

import java.net.InetSocketAddress;
import java.util.Optional;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * JUnit 5 {@link BeforeAllCallback} extension to ensure a running Cassandra server.
 *
 * @author Mark Paluch
 * @see CassandraKeyspace
 */
class CassandraExtension implements BeforeAllCallback {

	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
			.create(CassandraExtension.class);

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {

		ExtensionContext.Store store = context.getStore(NAMESPACE);
		CassandraKeyspace cassandra = findAnnotation(context);

		CassandraServer keyspace = store.getOrComputeIfAbsent(CassandraServer.class, it -> {
			return CassandraServer.embeddedIfNotRunning("localhost", 9042);
		}, CassandraServer.class);

		keyspace.before();

		CqlSession session = store.getOrComputeIfAbsent(CqlSession.class, it -> {

			return CqlSession.builder().addContactPoint(new InetSocketAddress("localhost", 9042))
					.withLocalDatacenter("datacenter1").build();
		}, CqlSession.class);

		session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s \n"
				+ "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", cassandra.keyspace()));
	}

	private static CassandraKeyspace findAnnotation(ExtensionContext context) {

		Class<?> testClass = context.getRequiredTestClass();

		Optional<CassandraKeyspace> annotation = AnnotationUtils.findAnnotation(testClass, CassandraKeyspace.class);

		return annotation.orElseThrow(() -> new IllegalStateException("Test class not annotated with @Cassandra"));
	}
}
