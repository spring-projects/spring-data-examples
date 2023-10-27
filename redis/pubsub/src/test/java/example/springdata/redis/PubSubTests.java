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

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Show usage of Redis Pub/Sub operations.
 *
 * @author Mark Paluch
 */
@SpringBootTest
public class PubSubTests {

	@Autowired RedisConnectionFactory connectionFactory;

	@Autowired StringRedisTemplate redisTemplate;

	@Test
	void shouldListenToPubSubEvents() throws Exception {

		BlockingQueue<String> events = new LinkedBlockingDeque<>();

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.afterPropertiesSet();
		container.addMessageListener(
				(message, pattern) -> events.add(String.format("%s@%s", new String(message.getBody()), new String(pattern))),
				ChannelTopic.of("my-channel"));

		container.start();

		redisTemplate.convertAndSend("my-channel", "Hello, world!");

		String event = events.poll(5, TimeUnit.SECONDS);

		container.stop();
		container.destroy();

		assertThat(event).isEqualTo("Hello, world!@my-channel");
	}

	@Test
	void shouldNotifyListener() throws Exception {

		BlockingQueue<String> events = new LinkedBlockingDeque<>();

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.afterPropertiesSet();

		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MyListener(events));
		messageListenerAdapter.afterPropertiesSet();
		messageListenerAdapter.setSerializer(StringRedisSerializer.UTF_8);
		container.addMessageListener(messageListenerAdapter, ChannelTopic.of("my-channel"));

		container.start();

		redisTemplate.convertAndSend("my-channel", "Hello, world!");

		String event = events.poll(5, TimeUnit.SECONDS);

		container.stop();
		container.destroy();

		assertThat(event).isEqualTo("Hello, world!@my-channel");
	}

	static class MyListener {
		private final Collection<String> events;

		public MyListener(Collection<String> events) {
			this.events = events;
		}

		public void handleMessage(String message, String channel) {
			events.add(String.format("%s@%s", message, channel));
		}
	}
}
