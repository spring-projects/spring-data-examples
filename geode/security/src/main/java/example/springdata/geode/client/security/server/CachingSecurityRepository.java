/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.geode.client.security.server;

import example.springdata.geode.client.security.User;

import java.util.HashMap;
import java.util.Map;

/**
 * The [CachingSecurityRepository] class caches Security Configuration Meta-Data and is meant to be extended
 * by classes that are data store specified (e.g. JDBC/RDBMS, LDAP, etc).
 *
 * @author John Blum
 * @see User
 * @see SecurityRepository
 * @since 1.0.0
 */
public abstract class CachingSecurityRepository implements SecurityRepository {

	private Map<String, User> users = new HashMap<>();

	@Override
	public Iterable<User> findAll() {
		return users.values();
	}

	@Override
	public boolean delete(User user) {
		if (user != null) {
			return users.remove(user.getName()) != null;
		}
		return false;
	}

	@Override
	public User save(User user) {
		var putUser = users.put(user.getName(), user);
		return putUser != null ? putUser : user;
	}
}
