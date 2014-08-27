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
package example.springdata.redis.commands;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.springdata.redis.RedisTestConfiguration;
import example.springdata.redis.test.util.RequiresRedisServer;

/**
 * Show usage of operations on redis keys using low level API provided by {@link RedisConnection}.
 * 
 * @author Christoph Strobl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RedisTestConfiguration.class })
public class KeyOperationsTests {

	// we only want to run this tests when redis is up an running
	public static @ClassRule RequiresRedisServer requiresServer = RequiresRedisServer.onLocalhost();

	private static final String PREFIX = KeyOperationsTests.class.getSimpleName();
	private static final String KEY_PATTERN = PREFIX + "*";

	@Autowired RedisConnectionFactory connectionFactory;

	private RedisConnection connection;
	private RedisSerializer<String> serializer = new StringRedisSerializer();

	@Before
	public void setUp() {
		this.connection = connectionFactory.getConnection();
	}

	/**
	 * Uses {@code KEYS} command for loading all matching keys. <br />
	 * Note that {@code KEYS} is a blocking command that potentially might affect other operations execution time. <br />
	 * All keys will be loaded within <strong>one single</strong> operation.
	 */
	@Test
	public void iterateOverKeysMatchingPrefixUsingKeysCommand() {

		generateRandomKeys(1000);

		Set<byte[]> keys = this.connection.keys(serializer.serialize(KEY_PATTERN));
		printKeys(keys.iterator());
	}

	/**
	 * Uses {@code SCAN} command for loading all matching keys. <br />
	 * {@code SCAN} uses a cursor on server side returning only a subset of the available data with the possibility to
	 * ripple load further elements using the cursors position. <br />
	 * All keys will be loaded using <strong>multiple</strong> operations.
	 */
	@Test
	public void iterateOverKeysMatchingPrefixUsingScanCommand() {

		generateRandomKeys(1000);

		Cursor<byte[]> cursor = this.connection.scan(ScanOptions.scanOptions().match(KEY_PATTERN).build());
		printKeys(cursor);
	}

	private void printKeys(Iterator<byte[]> keys) {

		int i = 0;
		while (keys.hasNext()) {
			System.out.println(new String(keys.next()));
			i++;
		}
		System.out.println(String.format("Total No. found: %s", i));
	}

	private void generateRandomKeys(int nrKeys) {

		for (int i = 0; i < nrKeys; i++) {
			this.connection.set((PREFIX + "-" + i).getBytes(), UUID.randomUUID().toString().getBytes());
		}
	}
}
