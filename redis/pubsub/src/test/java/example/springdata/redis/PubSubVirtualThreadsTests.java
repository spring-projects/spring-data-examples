/*
 * Copyright 2023 the original author or authors.
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
package example.springdata.redis;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Show usage of Redis Pub/Sub operations using Virtual Threads.
 *
 * @author Mark Paluch
 */
@SpringBootTest(properties = "spring.threads.virtual.enabled=true")
@EnabledOnJre(JRE.JAVA_21)
public class PubSubVirtualThreadsTests {

	@Autowired RedisConnectionFactory connectionFactory;

	@Autowired AsyncTaskExecutor taskExecutor;

	@Autowired StringRedisTemplate redisTemplate;

	@Test
	void shouldListenToPubSubEvents() throws Exception {

		BlockingQueue<String> events = new LinkedBlockingDeque<>();

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setTaskExecutor(taskExecutor);
		container.afterPropertiesSet();
		container.addMessageListener(
				(message, pattern) -> events
						.add(String.format("%s on Thread %s", new String(message.getBody()), Thread.currentThread())),
				ChannelTopic.of("my-channel"));

		container.start();

		redisTemplate.convertAndSend("my-channel", "Hello, world!");

		String event = events.poll(5, TimeUnit.SECONDS);

		container.stop();
		container.destroy();

		assertThat(event).isNotNull().contains("Hello, world!").contains("VirtualThread");
	}
}
