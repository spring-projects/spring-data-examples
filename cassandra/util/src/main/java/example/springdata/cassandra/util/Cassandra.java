/*
 * Copyright 2017-2018 the original author or authors.
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
package example.springdata.cassandra.util;

import java.util.concurrent.TimeUnit;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AssumptionViolatedException;

/**
 * {@link org.junit.rules.TestRule} for Cassandra server use. This rule can start a Cassandra instance, reuse a running
 * instance or simply require a running Cassandra server (will skip the test if Cassandra is not running).
 *
 * @author Mark Paluch
 */
public class Cassandra extends CassandraResource {

	private final RuntimeMode runtimeMode;

	private Cassandra(String host, int port, RuntimeMode runtimeMode) {

		super(host, port);
		this.runtimeMode = runtimeMode;
	}

	/**
	 * Require a running instance on {@code host:port}. Fails with {@link AssumptionViolatedException} if Cassandra is not
	 * running.
	 *
	 * @param host must not be {@literal null} or empty.
	 * @param port must be between 0 and 65535.
	 * @return the {@link Cassandra} rule
	 */
	public static Cassandra requireRunningInstance(String host, int port) {
		return new Cassandra(host, port, RuntimeMode.REQUIRE_RUNNING_INSTANCE);
	}

	/**
	 * Start an embedded Cassandra instance on {@code host:port} if Cassandra is not running already.
	 *
	 * @param host must not be {@literal null} or empty.
	 * @param port must be between 0 and 65535.
	 * @return the {@link Cassandra} rule
	 */
	public static Cassandra embeddedIfNotRunning(String host, int port) {
		return new Cassandra(host, port, RuntimeMode.EMBEDDED_IF_NOT_RUNNING);
	}

	@Override
	protected void before() throws Throwable {

		if (runtimeMode == RuntimeMode.REQUIRE_RUNNING_INSTANCE) {
			if (!CassandraSocket.isConnectable(getHost(), getPort())) {
				throw new AssumptionViolatedException(
						String.format("Cassandra is not reachable at %s:%s.", getHost(), getPort()));
			}
		}

		if (runtimeMode == RuntimeMode.EMBEDDED_IF_NOT_RUNNING) {
			if (CassandraSocket.isConnectable(getHost(), getPort())) {
				return;
			}
		}

		EmbeddedCassandraServerHelper.startEmbeddedCassandra("embedded-cassandra.yaml", "target/embeddedCassandra",
				TimeUnit.SECONDS.toMillis(60));
		super.before();
	}

	private enum RuntimeMode {
		REQUIRE_RUNNING_INSTANCE, EMBEDDED_IF_NOT_RUNNING;
	}
}
