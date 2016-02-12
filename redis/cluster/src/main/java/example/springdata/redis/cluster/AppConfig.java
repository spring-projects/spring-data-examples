/*
 * Copyright 2015-2016 the original author or authors.
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
package example.springdata.redis.cluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Application context configuration setting up {@link RedisConnectionFactory} and {@link RedisTemplate} according to
 * {@link ClusterConfigurationProperties}.
 * 
 * @author Christoph Strobl
 */
@Configuration
@EnableConfigurationProperties(ClusterConfigurationProperties.class)
public class AppConfig {

	/**
	 * Type safe representation of application.properties
	 */
	@Autowired ClusterConfigurationProperties clusterProperties;

	/**
	 * The connection factory used for obtaining {@link RedisConnection} uses a {@link RedisClusterConfiguration} that
	 * points to the initial set of nodes.
	 */
	@Bean
	RedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory(new RedisClusterConfiguration(clusterProperties.getNodes()));
	}

	/**
	 * {@link RedisTemplate} can be configured with {@link RedisSerializer} if needed. <br />
	 * <b>NOTE:</b> be careful using JSON @link RedisSerializer} for key serialization.
	 */
	@Bean
	RedisTemplate<String, String> redisTemplate() {
		return new StringRedisTemplate(connectionFactory());
	}
}
