/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.redis.reactive;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamReceiver;
import reactor.test.StepVerifier;

/**
 * @author Christoph Strobl
 */
@SpringBootApplication
public class ReactiveRedisTestConfiguration {

	@Autowired ReactiveRedisConnectionFactory factory;

	@Bean
	StreamReceiver<String, MapRecord<String, String, String>> streamReceiver() {
		return StreamReceiver.create(factory);
	}

	/**
	 * Clear database before shut down.
	 */
	public @PreDestroy
	void flushTestDb() {
		factory.getReactiveConnection().serverCommands().flushDb().then().as(StepVerifier::create).verifyComplete();
	}
}
