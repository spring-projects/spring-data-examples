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
import lombok.extern.apachecommons.CommonsLog;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.geode.security.ResourcePermission;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Patrick Johnson
 */
@Repository
@CommonsLog
public class JdbcSecurityRepository extends CachingSecurityRepository implements InitializingBean {

	private final JdbcTemplate jdbcTemplate;
	private static final String ROLES_QUERY = "SELECT name FROM geode_security.roles";
	private static final String ROLE_PERMISSIONS_QUERY = ""
			+ " SELECT rolePerms.resource, rolePerms.operation, rolePerms.region_name, rolePerms.key_name"
			+ " FROM geode_security.roles_permissions rolePerms"
			+ " INNER JOIN geode_security.roles roles ON roles.id = rolePerms.role_id " + " WHERE roles.name = ?";
	private static final String USERS_QUERY = "SELECT name, credentials FROM geode_security.users";
	private static final String USER_ROLES_QUERY = "" + " SELECT roles.name" + " FROM geode_security.roles roles"
			+ " INNER JOIN geode_security.users_roles userRoles ON roles.id = userRoles.role_id"
			+ " INNER JOIN geode_security.users users ON userRoles.user_id = users.id" + " WHERE users.name = ?";

	public JdbcSecurityRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void afterPropertiesSet() {

		var roles = this.jdbcTemplate.query(ROLES_QUERY, (resultSet, i) -> Role.newRole(resultSet.getString(1)));
		var roleMapping = new HashMap<String, Role>(roles.size());

		roles.forEach((role) -> {
			this.jdbcTemplate.query(ROLE_PERMISSIONS_QUERY, Collections.singleton(role.getName()).toArray(),
					(RowMapper<Object>) (resultSet, i) -> role.withPermissions(newResourcePermission(resultSet.getString(1),
							resultSet.getString(2), resultSet.getString(3), resultSet.getString(4))));

			roleMapping.put(role.getName(), role);
		});

		var users = this.jdbcTemplate.query(USERS_QUERY,
				(resultSet, i) -> createUser(resultSet.getString(1)).withCredentials(resultSet.getString(2)));

		users.forEach((role) -> {
			this.jdbcTemplate.query(USER_ROLES_QUERY, Collections.singleton(role.getName()).toArray(),
					(RowMapper<Object>) (resultSet, i) -> role.withRoles(roleMapping.get(resultSet.getString(1))));

			save(role);
		});

		log.debug(String.format("Users %s", users));
	}

	protected ResourcePermission newResourcePermission(String resource, String operation, String region, String key) {
		return new ResourcePermission(resource, operation, region, key);
	}
}
