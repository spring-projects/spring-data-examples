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
package example.springdata.redis.sentinel;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import example.springdata.redis.test.util.RequiresRedisSentinel;

/**
 * @author Christoph Strobl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RedisSentinelApplicationConfig.class })
public class RedisSentinelTests {

	// we only want to run this tests when at least one active sentinel can be found for provided configuration
	public static @ClassRule RequiresRedisSentinel requiresRunningSentinels = RequiresRedisSentinel.forConfig(
			RedisSentinelApplicationConfig.SENTINEL_CONFIG).oneActive();

	@Autowired RedisConnectionFactory factory;

	/*
	 * Show usage of RedisSentinelConfiguration.
	 * This test will run forever so that it allows the manually alter Redis server and sentinel instances.
	 */
	@Test
	public void redisSentinelUsage() throws InterruptedException {

		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(factory);
		template.afterPropertiesSet();

		template.opsForValue().set("loop-forever", "0");

		StopWatch stopWatch = new StopWatch();

		while (true) {
			try {
				String value = "IT:= " + template.opsForValue().increment("loop-forever", 1);
				printBackFromErrorStateInfoIfStopWatchIsRunning(stopWatch);
				System.out.println(value);
			} catch (RuntimeException e) {
				System.err.println(e.getCause().getMessage());
				startStopWatchIfNotRunning(stopWatch);
			}

			Thread.sleep(1000);
		}
	}

	private void startStopWatchIfNotRunning(StopWatch stopWatch) {

		if (!stopWatch.isRunning()) {
			stopWatch.start();
		}
	}

	private void printBackFromErrorStateInfoIfStopWatchIsRunning(StopWatch stopWatch) {

		if (stopWatch.isRunning()) {
			stopWatch.stop();
			System.err.println("INFO: Recovered after: " + stopWatch.getLastTaskInfo().getTimeSeconds());
		}
	}
}
