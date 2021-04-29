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
package example.springdata.redis.sentinel;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StopWatch;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Configuration
public class RedisSentinelApplication {

	static final RedisSentinelConfiguration SENTINEL_CONFIG = new RedisSentinelConfiguration().master("mymaster") //
			.sentinel("localhost", 26379) //
			.sentinel("localhost", 26380) //
			.sentinel("localhost", 26381);

	@Autowired RedisConnectionFactory factory;

	public static void main(String[] args) throws Exception {

		ApplicationContext context = SpringApplication.run(RedisSentinelApplication.class, args);

		var template = context.getBean(StringRedisTemplate.class);
		template.opsForValue().set("loop-forever", "0");

		var stopWatch = new StopWatch();

		while (true) {

			try {

				var value = "IT:= " + template.opsForValue().increment("loop-forever", 1);
				printBackFromErrorStateInfoIfStopWatchIsRunning(stopWatch);
				System.out.println(value);

			} catch (RuntimeException e) {

				System.err.println(e.getCause().getMessage());
				startStopWatchIfNotRunning(stopWatch);
			}

			Thread.sleep(1000);
		}
	}

	public @Bean StringRedisTemplate redisTemplate() {
		return new StringRedisTemplate(connectionFactory());
	}

	public @Bean RedisConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(sentinelConfig(), LettuceClientConfiguration.defaultConfiguration());
	}

	public @Bean RedisSentinelConfiguration sentinelConfig() {
		return SENTINEL_CONFIG;
	}

	/**
	 * Clear database before shut down.
	 */
	public @PreDestroy void flushTestDb() {
		factory.getConnection().flushDb();
	}

	private static void startStopWatchIfNotRunning(StopWatch stopWatch) {

		if (!stopWatch.isRunning()) {
			stopWatch.start();
		}
	}

	private static void printBackFromErrorStateInfoIfStopWatchIsRunning(StopWatch stopWatch) {

		if (stopWatch.isRunning()) {
			stopWatch.stop();
			System.err.println("INFO: Recovered after: " + stopWatch.getLastTaskInfo().getTimeSeconds());
		}
	}
}
