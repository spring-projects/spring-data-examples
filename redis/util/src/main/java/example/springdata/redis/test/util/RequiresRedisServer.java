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
package example.springdata.redis.test.util;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.ExternalResource;

/**
 * Implementation of junit rule {@link ExternalResource} to verify Redis (or at least something on the defined host and
 * port) is up and running.
 * 
 * @author Christoph Strobl
 */
public class RequiresRedisServer extends ExternalResource {

	private int timeout = 30;

	private final String host;
	private final int port;

	private RequiresRedisServer(String host, int port) {

		this.host = host;
		this.port = port;
	}

	public static RequiresRedisServer onLocalhost() {
		return new RequiresRedisServer("localhost", 6379);
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
			throw new AssumptionViolatedException(String.format("Seems as redis is not running at %s:%s.", host, port), e);
		}
	}
}
