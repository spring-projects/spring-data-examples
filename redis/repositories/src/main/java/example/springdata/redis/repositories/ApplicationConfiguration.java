/*
 * Copyright 2016 the original author or authors.
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
package example.springdata.redis.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author Christoph Strobl
 */
@Configuration
@EnableRedisRepositories
public class ApplicationConfiguration {

	@Bean
	RedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {

		RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
		template.setConnectionFactory(connectionFactory);

		return template;
	}
}
