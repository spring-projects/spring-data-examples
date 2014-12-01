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

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.springdata.redis.RedisTestConfiguration;
import example.springdata.redis.test.util.RequiresRedisServer;

/**
 * HyperLogLog ({@literal HLL}) is available for Redis 2.8.9+.
 * 
 * @author Christoph Strobl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RedisTestConfiguration.class })
public class HyperLogLogOperationsTests {

	public static @ClassRule RequiresRedisServer requiresServer = RequiresRedisServer.onLocalhost();

	private static final String KEY_1 = "HLL-1";
	private static final String KEY_2 = "HLL-2";
	private static final String KEY_3 = "HLL-3";

	@Autowired StringRedisTemplate redisTemplate;

	private HyperLogLogOperations<String, String> hllOps;

	@Before
	public void setUp() {

		redisTemplate.delete(Arrays.asList(KEY_1, KEY_2, KEY_3));
		this.hllOps = redisTemplate.opsForHyperLogLog();
	}

	/**
	 * Basic usage of HyperLogLog structure adding values while retaining size if already exists.
	 */
	@Test
	public void basicHyperLogLogTemplateUsage() {

		hllOps.add(KEY_1, "spring", "data");
		assertThat(hllOps.size(KEY_1), is(2L));

		hllOps.add(KEY_1, "redis");
		assertThat(hllOps.size(KEY_1), is(3L));

		hllOps.add(KEY_1, "spring");
		assertThat(hllOps.size(KEY_1), is(3L));
	}

	/**
	 * HyperLogLog allows distinct count of values stored at multiple keys.
	 */
	@Test
	public void countMultipleIndexes() {

		hllOps.add(KEY_1, "spring", "data");
		hllOps.add(KEY_2, "spring", "data", "redis");

		assertThat(hllOps.size(KEY_1, KEY_2), is(3L));
	}

	/**
	 * HyperLogLog can merge values of multiple keys into one distinct new one.
	 */
	@Test
	public void mergeMultipleIndexes() {

		hllOps.add(KEY_1, "spring", "data");
		hllOps.add(KEY_2, "spring", "data", "redis");

		hllOps.union(KEY_3, KEY_1, KEY_2);

		assertThat(hllOps.size(KEY_3), is(3L));
	}

}
