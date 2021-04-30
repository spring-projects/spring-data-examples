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
import java.util.concurrent.Callable;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import org.springframework.util.StringUtils;

import org.testcontainers.containers.CassandraContainer;

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

	private static CassandraContainer container;

	@Override
	public void beforeAll(ExtensionContext context) {

		var store = context.getStore(NAMESPACE);
		var cassandra = findAnnotation(context);

		var keyspace = store.getOrComputeIfAbsent(CassandraServer.class, it -> {

			CassandraContainer container = runTestcontainer();
			System.setProperty("spring.data.cassandra.port", "" + container.getMappedPort(9042));
			System.setProperty("spring.data.cassandra.contact-points", "" + container.getHost());

			return new CassandraServer(container.getHost(), container.getMappedPort(9042),
					CassandraServer.RuntimeMode.EMBEDDED_IF_NOT_RUNNING);
		}, CassandraServer.class);

		keyspace.before();


		Callable<CqlSession> sessionFactory = () -> CqlSession.builder()
				.addContactPoint(new InetSocketAddress(keyspace.host(), keyspace.port())).withLocalDatacenter("datacenter1")
				.build();
		Awaitility.await().ignoreExceptions().untilAsserted(() -> {

			sessionFactory.call().close();
		});

		var session = store.getOrComputeIfAbsent(CqlSession.class, it -> {

			try {
				return sessionFactory.call();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}, CqlSession.class);

		session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s \n"
				+ "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", cassandra.keyspace()));
	}

	private static CassandraKeyspace findAnnotation(ExtensionContext context) {

		var testClass = context.getRequiredTestClass();

		var annotation = AnnotationUtils.findAnnotation(testClass, CassandraKeyspace.class);

		return annotation.orElseThrow(() -> new IllegalStateException("Test class not annotated with @Cassandra"));
	}

	private CassandraContainer<?> runTestcontainer() {

		if (container != null) {
			return container;
		}

		container = new CassandraContainer<>(getCassandraDockerImageName());
		container.withReuse(true);

		container.start();

		return container;
	}

	private String getCassandraDockerImageName() {

		return String.format("cassandra:%s",
				Optional.ofNullable(System.getenv("CASSANDRA_VERSION")).filter(StringUtils::hasText).orElse("3.11.10"));
	}
}
