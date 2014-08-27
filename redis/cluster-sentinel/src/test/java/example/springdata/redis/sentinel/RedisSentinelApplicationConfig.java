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

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author Christoph Strobl
 */
@Configuration
public class RedisSentinelApplicationConfig {

	static final RedisSentinelConfiguration SENTINEL_CONFIG = new RedisSentinelConfiguration().master("mymaster") //
			.sentinel("localhost", 26379) //
			.sentinel("localhost", 26380) //
			.sentinel("localhost", 26381);

	@Autowired RedisConnectionFactory factory;

	@Bean
	public RedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory(sentinelConfig());
	}

	@Bean
	public RedisSentinelConfiguration sentinelConfig() {
		return SENTINEL_CONFIG;
	}

	/**
	 * Clear database before shut down.
	 */
	@PreDestroy
	public void flushTestDb() {
		factory.getConnection().flushDb();
	}
}
