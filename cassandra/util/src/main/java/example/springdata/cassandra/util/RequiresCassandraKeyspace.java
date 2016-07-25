/*
 * Copyright 2016 the original author or authors.
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
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.junit.AssumptionViolatedException;
import org.junit.rules.ExternalResource;
import org.springframework.data.util.Version;
import org.springframework.util.Assert;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.NettyOptions;
import com.datastax.driver.core.Session;

import io.netty.channel.EventLoopGroup;

/**
 * Implementation of an {@link ExternalResource} to verify Apache Cassandra is running and listening on the given
 * contact point. This rule also creates a keyspace if it not exists.
 *
 * @author Mark Paluch
 */
public class RequiresCassandraKeyspace extends ExternalResource {

	private final int timeout = 30;
	private final String host;
	private final int port;
	private final String keyspaceName;
	private Version requiresVersion;

	private RequiresCassandraKeyspace(String host, int port, String keyspaceName) {

		this.host = host;
		this.port = port;
		this.keyspaceName = keyspaceName;
	}

	/**
	 * Require a running Cassandra instance on {@code localhost:9042}.
	 *
	 * @return the {@link RequiresCassandraKeyspace} rule
	 */
	public static RequiresCassandraKeyspace onLocalhost() {
		return new RequiresCassandraKeyspace("localhost", 9042, "example");
	}

	/**
	 * Setup a version requirement.
	 *
	 * @param version must not be {@literal null}.
	 * @return the {@link RequiresCassandraKeyspace} rule
	 */
	public RequiresCassandraKeyspace atLeast(Version version) {

		Assert.notNull(version, "Required version must not be null!");

		this.requiresVersion = version;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.ExternalResource#before()
	 */
	@Override
	protected void before() throws Throwable {

		try (Socket socket = new Socket()) {
			socket.setTcpNoDelay(true);
			socket.setSoLinger(true, 0);
			socket.connect(new InetSocketAddress(host, port), timeout);

		} catch (Exception e) {
			throw new AssumptionViolatedException(String.format("Seems as Cassandra is not running at %s:%s.", host, port),
					e);
		}

		Cluster cluster = Cluster.builder().addContactPoint(host).withPort(port).withNettyOptions(new NettyOptions() {
			@Override
			public void onClusterClose(EventLoopGroup eventLoopGroup) {
				eventLoopGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS).syncUninterruptibly();
			}
		}).build();

		Session session = cluster.newSession();

		try {

			if (requiresVersion != null) {

				Version cassandraReleaseVersion = CassandraVersion.getReleaseVersion(session);

				if (cassandraReleaseVersion.isLessThan(requiresVersion)) {
					throw new AssumptionViolatedException(
							String.format("Cassandra at %s:%s runs in Version %s but we require at least %s", host, port,
									cassandraReleaseVersion, requiresVersion));
				}
			}

			session.execute(String.format("CREATE KEYSPACE IF NOT EXISTS %s \n"
					+ "WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };", keyspaceName));
		} finally {
			session.close();
			cluster.close();
		}
	}

}
