/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.redis.commands;

import example.springdata.redis.test.condition.EnabledOnRedisAvailable;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Show usage of operations on redis keys using low level API provided by {@link RedisConnection}.
 *
 * @author Christoph Strobl
 */
@DataRedisTest
@EnabledOnRedisAvailable
class KeyOperationsTests {

	private static final String PREFIX = KeyOperationsTests.class.getSimpleName();
	private static final String KEY_PATTERN = PREFIX + "*";

	@Autowired RedisConnectionFactory connectionFactory;

	private RedisConnection connection;
	private RedisSerializer<String> serializer = new StringRedisSerializer();

	@BeforeEach
	void setUp() {
		this.connection = connectionFactory.getConnection();
	}

	/**
	 * Uses {@code KEYS} command for loading all matching keys. <br />
	 * Note that {@code KEYS} is a blocking command that potentially might affect other operations execution time. <br />
	 * All keys will be loaded within <strong>one single</strong> operation.
	 */
	@Test
	void iterateOverKeysMatchingPrefixUsingKeysCommand() {

		generateRandomKeys(1000);

		var keys = this.connection.keys(serializer.serialize(KEY_PATTERN));
	}

	/**
	 * Uses {@code SCAN} command for loading all matching keys. <br />
	 * {@code SCAN} uses a cursor on server side returning only a subset of the available data with the possibility to
	 * ripple load further elements using the cursors position. <br />
	 * All keys will be loaded using <strong>multiple</strong> operations.
	 */
	@Test
	void iterateOverKeysMatchingPrefixUsingScanCommand() {

		generateRandomKeys(1000);

		this.connection.scan(ScanOptions.scanOptions().match(KEY_PATTERN).build());
	}

	private void generateRandomKeys(int nrKeys) {

		for (var i = 0; i < nrKeys; i++) {
			this.connection.set((PREFIX + "-" + i).getBytes(), UUID.randomUUID().toString().getBytes());
		}
	}
}
