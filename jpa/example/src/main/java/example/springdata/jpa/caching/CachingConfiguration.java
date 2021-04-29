/*
 * Copyright 2013-2021 the original author or authors.
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
package example.springdata.jpa.caching;

import java.util.Collections;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * Java config to use Spring Data JPA alongside the Spring caching support.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@EnableCaching
@SpringBootApplication
class CachingConfiguration {

	@Bean
	public CacheManager cacheManager() {

		Cache cache = new ConcurrentMapCache("byUsername");

		var manager = new SimpleCacheManager();
		manager.setCaches(Collections.singletonList(cache));

		return manager;
	}
}
