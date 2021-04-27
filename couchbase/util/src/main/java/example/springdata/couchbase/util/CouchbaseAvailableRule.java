/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.couchbase.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;

import javax.net.SocketFactory;

import org.junit.AssumptionViolatedException;
import org.junit.rules.ExternalResource;


/**
 * Rule to check Couchbase server availability. If Couchbase is not running, tests are skipped.
 *
 * @author Mark Paluch
 */
public class CouchbaseAvailableRule extends ExternalResource {

	private final String host;
	private final int port;
	private final Duration timeout = Duration.ofSeconds(1);

	private CouchbaseAvailableRule(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * Create a new rule requiring Couchbase running on {@code localhost} on {@code 8091}.
	 *
	 * @return the test rule.
	 */
	public static CouchbaseAvailableRule onLocalhost() {
		return new CouchbaseAvailableRule("localhost", 8091);
	}

	@Override
	protected void before() throws Throwable {

		Socket socket = SocketFactory.getDefault().createSocket();
		try {
			socket.connect(new InetSocketAddress(host, port), Math.toIntExact(timeout.toMillis()));
		} catch (IOException e) {
			throw new AssumptionViolatedException(
					String.format("Couchbase not available on on %s:%d. Skipping tests.", host, port), e);
		} finally {
			socket.close();
		}
	}
}
