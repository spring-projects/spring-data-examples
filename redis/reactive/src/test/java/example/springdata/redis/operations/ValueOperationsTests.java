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
package example.springdata.redis.operations;

import static org.assertj.core.api.Assertions.*;

import example.springdata.redis.RedisTestConfiguration;
import example.springdata.redis.test.condition.EnabledOnRedisAvailable;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisOperations;

/**
 * Show usage of reactive Template API on Redis strings using {@link ReactiveRedisOperations}.
 *
 * @author Mark Paluch
 */
@Slf4j
@SpringBootTest(classes = RedisTestConfiguration.class)
@EnabledOnRedisAvailable
class ValueOperationsTests {

	@Autowired ReactiveRedisOperations<String, String> operations;

	@BeforeEach
	void before() {
		StepVerifier.create(operations.execute(it -> it.serverCommands().flushDb())).expectNext("OK").verifyComplete();
	}

	/**
	 * Implement a simple caching sequence using {@code GET} and {@code SETEX} commands.
	 */
	@Test
	void shouldCacheValue() {

		var cacheKey = "foo";

		var valueOperations = operations.opsForValue();

		var cachedMono = valueOperations.get(cacheKey) //
				.switchIfEmpty(cacheValue().flatMap(it -> {

					return valueOperations.set(cacheKey, it, Duration.ofSeconds(60)).then(Mono.just(it));
				}));

		log.info("Initial access (takes a while...)");

		StepVerifier.create(cachedMono).expectSubscription() //
				.expectNoEvent(Duration.ofSeconds(9)) //
				.expectNext("Hello, World!") //
				.verifyComplete();

		log.info("Subsequent access (use cached value)");

		var duration = StepVerifier.create(cachedMono) //
				.expectNext("Hello, World!") //
				.verifyComplete();

		log.info("Done");

		assertThat(duration).isLessThan(Duration.ofSeconds(2));
	}

	/**
	 * @return the cache value that is expensive to calculate.
	 */
	private Mono<String> cacheValue() {
		return Mono.delay(Duration.ofSeconds(10)).then(Mono.just("Hello, World!"));
	}
}
