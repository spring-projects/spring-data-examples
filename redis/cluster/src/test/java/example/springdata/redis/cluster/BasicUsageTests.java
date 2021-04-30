/*
 * Copyright 2015-2021 the original author or authors.
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
package example.springdata.redis.cluster;

import static org.assertj.core.api.Assertions.*;

import example.springdata.redis.test.condition.EnabledOnRedisClusterAvailable;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * {@link BasicUsageTests} shows general usage of {@link RedisTemplate} and {@link RedisOperations} in a clustered
 * environment.
 *
 * @author Christoph Strobl
 */
@SpringBootTest(classes = { AppConfig.class })
@EnabledOnRedisClusterAvailable(port = 30001)
class BasicUsageTests {

	@Autowired RedisTemplate<String, String> template;

	@BeforeEach
	void setUp() {

		template.execute((RedisCallback<String>) connection -> {
			connection.flushDb();
			return "FLUSHED";
		});
	}

	/**
	 * Operation executed on a single node and slot. <br />
	 * -&gt; {@code SLOT 5798} served by {@code 127.0.0.1:30002}
	 */
	@Test
	void singleSlotOperation() {

		template.opsForValue().set("name", "rand al'thor"); // slot 5798
		assertThat(template.opsForValue().get("name")).isEqualTo("rand al'thor");
	}

	/**
	 * Operation executed on multiple nodes and slots. <br />
	 * -&gt; {@code SLOT 5798} served by {@code 127.0.0.1:30002} <br />
	 * -&gt; {@code SLOT 14594} served by {@code 127.0.0.1:30003}
	 */
	@Test
	void multiSlotOperation() {

		template.opsForValue().set("name", "matrim cauthon"); // slot 5798
		template.opsForValue().set("nickname", "prince of the ravens"); // slot 14594

		assertThat(template.opsForValue().multiGet(Arrays.asList("name", "nickname"))).contains("matrim cauthon",
				"prince of the ravens");
	}

	/**
	 * Operation executed on a single node and slot because of pinned slot key <br />
	 * -&gt; {@code SLOT 5798} served by {@code 127.0.0.1:30002}
	 */
	@Test
	void fixedSlotOperation() {

		template.opsForValue().set("{user}.name", "perrin aybara"); // slot 5474
		template.opsForValue().set("{user}.nickname", "wolfbrother"); // slot 5474

		assertThat(template.opsForValue().multiGet(Arrays.asList("{user}.name", "{user}.nickname")))
				.contains("perrin aybara", "wolfbrother");
	}

	/**
	 * Operation executed across the cluster to retrieve cumulated result. <br />
	 * -&gt; {@code KEY age} served by {@code 127.0.0.1:30001} <br />
	 * -&gt; {@code KEY name} served by {@code 127.0.0.1:30002} <br />
	 * -&gt; {@code KEY nickname} served by {@code 127.0.0.1:30003}
	 */
	@Test
	void multiNodeOperation() {

		template.opsForValue().set("name", "rand al'thor"); // slot 5798
		template.opsForValue().set("nickname", "dragon reborn"); // slot 14594
		template.opsForValue().set("age", "23"); // slot 741;

		assertThat(template.keys("*")).contains("name", "nickname", "age");
	}
}
