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

import example.springdata.geode.client.security.Role;
import example.springdata.geode.client.security.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The [SecurityRepository] interface is a contract for Data Access Objects (DAO) implementing this interface to perform
 * CRUD and query operations on [User] information, pertinent to the security of the system.
 *
 * @author John Blum
 * @author Udo Kohlmeyer
 * @see Role
 * @see User
 * @see org.springframework.stereotype.Repository
 * @since 1.0.0
 */
public interface SecurityRepository {

	/**
	 * Finds all [Users][User] of the system.
	 *
	 * @return an [Iterable] of [Users][User] of the system.
	 * @see User
	 * @see Iterable
	 */
	Iterable<User> findAll();

	/**
	 * Deletes the given [User] from the system.
	 *
	 * @param user [User] to delete.
	 * @return a boolean value indicating if the [User] was deleted successfully.
	 * @see User
	 */
	boolean delete(User user);

	/**
	 * Records (persist) the information (state) of the [User].
	 *
	 * @param user [User] to store.
	 * @return the [User].
	 * @see User
	 */
	User save(User user);

	/* (non-Javadoc) */
	default int count() {
		var count = 0;
		var users = findAll();
		for (var user : users) {
			count++;
		}
		return count;
	}

	/* (non-Javadoc) */
	default User createUser(String username, Role... roles) {
		return save(User.newUser(username).withRoles(roles));
	}

	/* (non-Javadoc) */
	default boolean delete(String username) {
		var user = findBy(username);
		if (user != null) {
			return delete(user);
		}
		return false;
	}

	/* (non-Javadoc) */
	default boolean deleteAll(String... username) {
		return deleteAll(findAll(username));
	}

	/* (non-Javadoc) */
	default boolean deleteAll(User... users) {
		return deleteAll(Arrays.asList(users));
	}

	/* (non-Javadoc) */
	default boolean deleteAll(Iterable<User> users) {
		for (var user : users) {
			if (!delete(user)) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc) */
	default boolean deleteAll() {
		return deleteAll(findAll());
	}

	/* (non-Javadoc) */
	default boolean exists(String username) {
		return findBy(username) != null;
	}

	/* (non-Javadoc) */
	default Iterable<User> findAll(String... username) {
		return findAll(Arrays.asList(username));
	}

	/* (non-Javadoc) */
	default Iterable<User> findAll(Iterable<String> username) {
		List<User> all = new ArrayList<>();
		username.forEach(u -> {
			if (u != null) {
				all.add(findBy(u));
			}
		});
		return all;
	}

	/* (non-Javadoc) */
	default User findBy(String username) {
		for (var user : findAll()) {
			if (user.getName().equals(username)) {
				return user;
			}
		}
		return null;
	}

	/* (non-Javadoc) */
	default Iterable<User> saveAll(User... users) {
		return saveAll(Arrays.asList(users));
	}

	/* (non-Javadoc) */
	default Iterable<User> saveAll(Iterable<User> users) {
		List<User> saved = new ArrayList<>();
		users.forEach(user -> saved.add(save(user)));
		return saved;
	}
}
