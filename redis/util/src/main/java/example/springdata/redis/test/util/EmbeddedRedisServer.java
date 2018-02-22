/*
 * Copyright 2016-2018 the original author or authors.
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

import java.io.IOException;

import org.junit.rules.ExternalResource;

import redis.embedded.RedisServer;

/**
 * JUnit rule implementation to start and shut down an embedded Redis instance.
 *
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
public class EmbeddedRedisServer extends ExternalResource {

	private static final int DEFAULT_PORT = 6379;
	private RedisServer server;
	private int port = DEFAULT_PORT;
	private boolean suppressExceptions = false;

	public EmbeddedRedisServer() {

	}

	protected EmbeddedRedisServer(int port) {
		this.port = port;
	}

	public static EmbeddedRedisServer runningAt(Integer port) {
		return new EmbeddedRedisServer(port != null ? port : DEFAULT_PORT);
	}

	public EmbeddedRedisServer suppressExceptions() {
		this.suppressExceptions = true;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.ExternalResource#before()
	 */
	@Override
	protected void before() throws IOException {

		try {

			this.server = new RedisServer(this.port);
			this.server.start();
		} catch (Exception e) {
			if (!suppressExceptions) {
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.ExternalResource#after()
	 */
	@Override
	protected void after() {

		try {
			this.server.stop();
		} catch (Exception e) {
			if (!suppressExceptions) {
				throw e;
			}
		}
	}
}
