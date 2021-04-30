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

import example.springdata.redis.EmailAddress;
import example.springdata.redis.Person;
import example.springdata.redis.RedisTestConfiguration;
import example.springdata.redis.test.condition.EnabledOnRedisAvailable;
import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.util.ByteUtils;

/**
 * Show usage of reactive Template API on Redis lists using {@link ReactiveRedisOperations} with Jackson serialization.
 *
 * @author Mark Paluch
 */
@Slf4j
@SpringBootTest(classes = RedisTestConfiguration.class)
@EnabledOnRedisAvailable
class JacksonJsonTests {

	@Autowired ReactiveRedisOperations<String, Person> typedOperations;

	@Autowired ReactiveRedisOperations<String, Object> genericOperations;

	/**
	 * {@link ReactiveRedisOperations} using {@link String} keys and {@link Person} values serialized via
	 * {@link org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer} to JSON without additional type
	 * hints.
	 *
	 * @see RedisTestConfiguration#reactiveJsonPersonRedisTemplate(ReactiveRedisConnectionFactory)
	 */
	@Test
	void shouldWriteAndReadPerson() {

		StepVerifier.create(typedOperations.opsForValue().set("homer", new Person("Homer", "Simpson"))) //
				.expectNext(true) //
				.verifyComplete();

		var get = typedOperations.execute(conn -> conn.stringCommands().get(ByteBuffer.wrap("homer".getBytes()))) //
				.map(ByteUtils::getBytes) //
				.map(String::new);

		get.as(StepVerifier::create) //
				.expectNext("{\"firstname\":\"Homer\",\"lastname\":\"Simpson\"}") //
				.verifyComplete();

		typedOperations.opsForValue().get("homer").as(StepVerifier::create) //
				.expectNext(new Person("Homer", "Simpson")) //
				.verifyComplete();
	}

	/**
	 * {@link ReactiveRedisOperations} using {@link String} keys and {@link Object} values serialized via
	 * {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} to JSON with additional type
	 * hints. This example uses the non-final type {@link Person} using its FQCN as type identifier.
	 *
	 * @see RedisTestConfiguration#reactiveJsonObjectRedisTemplate(ReactiveRedisConnectionFactory)
	 */
	@Test
	void shouldWriteAndReadPersonObject() {

		genericOperations.opsForValue().set("homer", new Person("Homer", "Simpson")) //
				.as(StepVerifier::create) //
				.expectNext(true) //
				.verifyComplete();

		var get = genericOperations.execute(conn -> conn.stringCommands().get(ByteBuffer.wrap("homer".getBytes()))) //
				.map(ByteUtils::getBytes) //
				.map(String::new);

		get.as(StepVerifier::create) //
				.expectNext("{\"_type\":\"example.springdata.redis.Person\",\"firstname\":\"Homer\",\"lastname\":\"Simpson\"}") //
				.verifyComplete();

		genericOperations.opsForValue().get("homer").as(StepVerifier::create) //
				.expectNext(new Person("Homer", "Simpson")) //
				.verifyComplete();
	}

	/**
	 * {@link ReactiveRedisOperations} using {@link String} keys and {@link Object} values serialized via
	 * {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} to JSON with additional type
	 * hints. This example uses the final type {@link example.springdata.redis.EmailAddress} using configuration from
	 * {@link com.fasterxml.jackson.annotation.JsonTypeInfo} as type identifier.
	 *
	 * @see RedisTestConfiguration#reactiveJsonObjectRedisTemplate(ReactiveRedisConnectionFactory)
	 */
	@Test
	void shouldWriteAndReadEmailObject() {

		genericOperations.opsForValue().set("mail", new EmailAddress("homer@the-simpsons.com")) //
				.as(StepVerifier::create) //
				.expectNext(true) //
				.verifyComplete();

		var get = genericOperations.execute(conn -> conn.stringCommands().get(ByteBuffer.wrap("mail".getBytes()))) //
				.map(ByteUtils::getBytes) //
				.map(String::new);

		get.as(StepVerifier::create) //
				.expectNext("{\"_type\":\"example.springdata.redis.EmailAddress\",\"address\":\"homer@the-simpsons.com\"}") //
				.verifyComplete();

		genericOperations.opsForValue().get("mail") //
				.as(StepVerifier::create) //
				.expectNext(new EmailAddress("homer@the-simpsons.com")) //
				.verifyComplete();
	}
}
