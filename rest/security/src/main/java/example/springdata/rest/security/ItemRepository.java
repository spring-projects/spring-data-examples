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
package example.springdata.rest.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * This repository shows interface and method-level security. The entire repository requires ROLE_USER, while certain
 * operations require ROLE_ADMIN.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
@PreAuthorize("hasRole('ROLE_USER')")
public interface ItemRepository extends CrudRepository<Item, Long> {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#save(S)
	 */
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	<S extends Item> S save(S s);

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
	 */
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Long aLong);
}
