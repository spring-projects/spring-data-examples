/*
 * Copyright 2014-2018 the original author or authors.
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
package example.springdata.redis.test.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;

import org.junit.AssumptionViolatedException;
import org.junit.rules.ExternalResource;
import org.springframework.data.redis.connection.lettuce.LettuceConverters;
import org.springframework.data.util.Version;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Implementation of junit rule {@link ExternalResource} to verify Redis (or at least something on the defined host and
 * port) is up and running. Allows optionally to require a specific Redis version.
 *
 * @author Christoph Strobl
 * @author Mark Paluch
 */
public class RequiresRedisServer extends ExternalResource {

	public static final Version NO_VERSION = Version.parse("0.0.0");

	private int timeout = 30;
	private Version requiredVersion = NO_VERSION;

	private final String host;
	private final int port;

	private RequiresRedisServer(String host, int port) {
		this(host, port, NO_VERSION);
	}

	private RequiresRedisServer(String host, int port, Version requiredVersion) {

		this.host = host;
		this.port = port;
		this.requiredVersion = requiredVersion;
	}

	/**
	 * Require a Redis instance listening on {@code localhost:6379}.
	 *
	 * @return
	 */
	public static RequiresRedisServer onLocalhost() {
		return new RequiresRedisServer("localhost", 6379);
	}

	/**
	 * Require a Redis instance listening {@code host:port}.
	 *
	 * @param host
	 * @param port
	 * @return
	 */
	public static RequiresRedisServer listeningAt(String host, int port) {
		return new RequiresRedisServer(StringUtils.hasText(host) ? host : "127.0.0.1", port);
	}

	/**
	 * Require a specific Redis version.
	 *
	 * @param version must not be {@literal null} or empty.
	 * @return
	 */
	public RequiresRedisServer atLeast(String version) {

		Assert.hasText(version, "Version must not be empty!");

		return new RequiresRedisServer(host, port, Version.parse(version));
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
			throw new AssumptionViolatedException(String.format("Seems as Redis is not running at %s:%s.", host, port), e);
		}

		if (NO_VERSION.equals(requiredVersion)) {
			return;
		}

		RedisClient redisClient = RedisClient.create(ManagedClientResources.getClientResources(),
				RedisURI.create(host, port));

		try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {

			String infoServer = connection.sync().info("server");
			String redisVersion = LettuceConverters.stringToProps().convert(infoServer).getProperty("redis_version");
			Version runningVersion = Version.parse(redisVersion);

			if (runningVersion.isLessThan(requiredVersion)) {
				throw new AssumptionViolatedException(String
						.format("This test requires Redis version %s but you run version %s", requiredVersion, runningVersion));
			}

		} finally {
			redisClient.shutdown(Duration.ZERO, Duration.ZERO);
		}
	}
}
