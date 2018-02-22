/*
 * Copyright 2017-2018 the original author or authors.
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

import org.junit.rules.ExternalResource;
import org.springframework.util.Assert;

/**
 * Base class to abstract contact point details for Apache Cassandra as {@link ExternalResource}.
 *
 * @author Mark Paluch
 */
public abstract class CassandraResource extends ExternalResource {

	private final String host;
	private final int port;

	CassandraResource(String host, int port) {

		Assert.hasText(host, "Host must not be null or empty!");
		Assert.isTrue(port >= 0 && port <= 65535, "Port must be in the range of 0..65535!");

		this.host = host;
		this.port = port;
	}

	/**
	 * @return the Cassandra hostname.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the Cassandra port.
	 */
	public int getPort() {
		return port;
	}

	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.ExternalResource#before()
	 */
	@Override
	protected void before() throws Throwable {
		super.before();
	}

	/*
	 * (non-Javadoc)
	 * @see org.junit.rules.ExternalResource#after()
	 */
	@Override
	protected void after() {
		super.after();
	}
}
