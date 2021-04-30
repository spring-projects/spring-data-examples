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

import example.springdata.redis.RedisTestConfiguration;
import example.springdata.redis.test.condition.EnabledOnRedisAvailable;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.logging.Level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisOperations;

/**
 * Show usage of reactive Template API on Redis lists using {@link ReactiveRedisOperations}.
 *
 * @author Mark Paluch
 */
@Slf4j
@SpringBootTest(classes = RedisTestConfiguration.class)
@EnabledOnRedisAvailable
class ListOperationsTests {

	@Autowired ReactiveRedisOperations<String, String> operations;

	@BeforeEach
	void before() {
		StepVerifier.create(operations.execute(it -> it.serverCommands().flushDb())).expectNext("OK").verifyComplete();
	}

	/**
	 * A simple queue using Redis blocking list commands {@code BLPOP} and {@code LPUSH} to produce the queue message.
	 */
	@Test
	void shouldPollAndPopulateQueue() {

		var queue = "foo";

		var listOperations = operations.opsForList();

		var blpop = listOperations //
				.leftPop(queue, Duration.ofSeconds(30)) //
				.log("example.springdata.redis", Level.INFO);

		log.info("Blocking pop...waiting for message");
		StepVerifier.create(blpop) //
				.then(() -> {

					Mono.delay(Duration.ofSeconds(10)).doOnSuccess(it -> {

						log.info("Subscriber produces message");

					}).then(listOperations.leftPush(queue, "Hello, World!")).subscribe();

				}).expectNext("Hello, World!").verifyComplete();

		log.info("Blocking pop...done!");
	}
}
