# Spring Data Redis - Sentinel Examples

This project contains samples of Sentinel specific features of Spring Data Redis.

## Support for Sentinel

```java
@Configuration
public class RedisSentinelApplicationConfig {

	static final RedisSentinelConfiguration SENTINEL_CONFIG = new RedisSentinelConfiguration().master("mymaster") //
			.sentinel("localhost", 26379) //
			.sentinel("localhost", 26380) //
			.sentinel("localhost", 26381);

	@Bean
	public RedisConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(sentinelConfig(), LettuceClientConfiguration.defaultConfiguration());
	}

	@Bean
	public RedisSentinelConfiguration sentinelConfig() {
		return SENTINEL_CONFIG;
	}

}
```

