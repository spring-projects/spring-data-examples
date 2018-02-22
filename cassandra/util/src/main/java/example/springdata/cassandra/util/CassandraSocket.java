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

import lombok.experimental.UtilityClass;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.springframework.util.Assert;

/**
 * @author Mark Paluch
 */
@UtilityClass
class CassandraSocket {

	/**
	 * @param host must not be {@literal null} or empty.
	 * @param port
	 * @return {@literal true} if the TCP port accepts a connection.
	 */
	public static boolean isConnectable(String host, int port) {

		Assert.hasText(host, "Host must not be null or empty!");

		try (Socket socket = new Socket()) {

			socket.setSoLinger(true, 0);
			socket.connect(new InetSocketAddress(host, port), (int) TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS));

			return true;

		} catch (Exception e) {
			return false;
		}
	}
}
