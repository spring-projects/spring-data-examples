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

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * User repository using Spring's caching abstraction.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Andrea Rizzini
 */
public interface CachingUserRepository extends CrudRepository<User, Long> {

	@Override
	@CacheEvict(value = "byUsername", key = "#p0.username")
	<S extends User> S save(S entity);

	@Cacheable("byUsername")
	User findByUsername(String username);
}
