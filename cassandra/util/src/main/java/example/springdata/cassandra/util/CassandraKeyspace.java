/*
 * Copyright 2016-2018 the original author or authors.
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

import java.net.InetSocketAddress;

import org.junit.AssumptionViolatedException;

import org.springframework.data.util.Version;
import org.springframework.util.Assert;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * {@link CassandraResource} to require (create or reuse) an Apache Cassandra keyspace and optionally require a specific
 * Apache Cassandra version. This {@link org.junit.rules.TestRule} can be chained to depend on another
 * {@link CassandraResource} rule to require a running instance/start an embedded Apache Cassandra instance.
 *
 * @author Mark Paluch
 */
public class CassandraKeyspace extends CassandraResource {

	private final String keyspaceName;
	private final Version requiredVersion;
	private final CassandraResource dependency;

	private CassandraKeyspace(String host, int port, String keyspaceName, CassandraResource dependency,
			Version requiredVersion) {

		super(host, port);

		this.keyspaceName = keyspaceName;
		this.dependency = dependency;
		this.requiredVersion = requiredVersion;
	}

	/**
	 * Create a {@link CassandraKeyspace} test rule to provide a running Cassandra instance on {@code localhost:9042} with
	 * a keyspace {@code example}. Reuses a running Cassandra instance if available or starts an embedded instance.
	 *
	 * @return the {@link CassandraKeyspace} rule.
	 */
	public static CassandraKeyspace onLocalhost() {
		return new CassandraKeyspace("localhost", 9042, "example", Cassandra.embeddedIfNotRunning("localhost", 9042),
				new Version(0, 0, 0));
	}

	/**
	 * Setup a dependency to an upstream {@link CassandraResource}. The dependency is activated by {@code this} test rule.
	 *
	 * @param cassandraResource must not be {@literal null}.
	 * @return the {@link CassandraKeyspace} rule.
	 */
	public CassandraKeyspace dependsOn(CassandraResource cassandraResource) {

		Assert.notNull(cassandraResource, "CassandraResource must not be null!");

		return new CassandraKeyspace(getHost(), getPort(), keyspaceName, cassandraResource, requiredVersion);
	}

	/**
	 * Setup a version requirement.
	 *
	 * @param requiredVersion must not be {@literal null}.
	 * @return the {@link CassandraKeyspace} rule
	 */
	public CassandraKeyspace atLeast(Version requiredVersion) {

		Assert.notNull(requiredVersion, "Required version must not be null!");

		return new CassandraKeyspace(getHost(), getPort(), keyspaceName, dependency, requiredVersion);
	}

	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.ExternalResource#before()
	 */
	@Override
	protected void before() throws Throwable {

		dependency.before();

		try (CqlSession session = CqlSession.builder().addContactPoint(new InetSocketAddress(getHost(), getPort()))
				.withLocalDatacenter("datacenter1").build()) {

			if (requiredVersion != null) {

				Version cassandraReleaseVersion = CassandraVersion.getReleaseVersion(session);

				if (cassandraReleaseVersion.isLessThan(requiredVersion)) {
					throw new AssumptionViolatedException(
							String.format("Cassandra at %s:%s runs in Version %s but we require at least %s", getHost(), getPort(),
									cassandraReleaseVersion, requiredVersion));
				}
			}

			session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s \n"
					+ "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", keyspaceName));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.ExternalResource#after()
	 */
	@Override
	protected void after() {

		super.after();
		dependency.after();
	}
}
