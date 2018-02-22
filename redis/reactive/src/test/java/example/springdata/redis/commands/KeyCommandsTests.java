/*
 * Copyright 2016-2018 the original author or authors.
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

import example.springdata.redis.RedisTestConfiguration;
import example.springdata.redis.test.util.RequiresRedisServer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ReactiveListCommands.PopResult;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveStringCommands.SetCommand;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.util.ByteUtils;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Show usage of reactive operations on Redis keys using low level API provided by
 * {@link ReactiveRedisConnectionFactory}.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisTestConfiguration.class)
public class KeyCommandsTests {

	// we only want to run this tests when redis is up an running
	public static @ClassRule RequiresRedisServer requiresServer = RequiresRedisServer.onLocalhost();

	private static final String PREFIX = KeyCommandsTests.class.getSimpleName();
	private static final String KEY_PATTERN = PREFIX + "*";

	@Autowired ReactiveRedisConnectionFactory connectionFactory;

	private ReactiveRedisConnection connection;
	private RedisSerializer<String> serializer = new StringRedisSerializer();

	@Before
	public void setUp() {
		this.connection = connectionFactory.getReactiveConnection();
	}

	/**
	 * Uses {@code KEYS} command for loading all matching keys. <br />
	 * Note that {@code KEYS} is a blocking command that potentially might affect other operations execution time. <br />
	 * All keys will be loaded within <strong>one single</strong> operation.
	 */
	@Test
	public void iterateOverKeysMatchingPrefixUsingKeysCommand() {

		generateRandomKeys(50);

		Mono<Long> keyCount = connection.keyCommands() //
				.keys(ByteBuffer.wrap(serializer.serialize(KEY_PATTERN))) //
				.flatMapMany(Flux::fromIterable) //
				.doOnNext(byteBuffer -> System.out.println(toString(byteBuffer))) //
				.count() //
				.doOnSuccess(count -> System.out.println(String.format("Total No. found: %s", count)));

		StepVerifier.create(keyCount).expectNext(50L).verifyComplete();
	}

	/**
	 * Uses {@code RPUSH} to store an item inside a list and {@code BRPOP} <br />
	 */
	@Test
	public void storeToListAndPop() {

		Mono<PopResult> popResult = connection.listCommands()
				.brPop(Collections.singletonList(ByteBuffer.wrap("list".getBytes())), Duration.ofSeconds(5));

		Mono<Long> llen = connection.listCommands().lLen(ByteBuffer.wrap("list".getBytes()));

		Mono<Long> popAndLlen = connection.listCommands() //
				.rPush(ByteBuffer.wrap("list".getBytes()), Collections.singletonList(ByteBuffer.wrap("item".getBytes())))
				.flatMap(l -> popResult) //
				.doOnNext(result -> System.out.println(toString(result.getValue()))) //
				.flatMap(result -> llen) //
				.doOnNext(count -> System.out.println(String.format("Total items in list left: %s", count)));//

		StepVerifier.create(popAndLlen).expectNext(0L).verifyComplete();
	}

	private void generateRandomKeys(int nrKeys) {

		Flux<String> keyFlux = Flux.range(0, nrKeys).map(i -> (PREFIX + "-" + i));

		Flux<SetCommand> generator = keyFlux.map(String::getBytes).map(ByteBuffer::wrap) //
				.map(key -> SetCommand.set(key) //
						.value(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes())));

		StepVerifier.create(connection.stringCommands().set(generator)).expectNextCount(nrKeys).verifyComplete();

	}

	private static String toString(ByteBuffer byteBuffer) {
		return new String(ByteUtils.getBytes(byteBuffer));
	}
}
