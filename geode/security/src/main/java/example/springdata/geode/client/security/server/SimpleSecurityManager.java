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

import java.util.Properties;

import org.apache.geode.security.AuthenticationFailedException;
import org.apache.geode.security.ResourcePermission;

import org.springframework.stereotype.Service;

/**
 * The [SimpleSecurityManager] class is an example Apache Geode [SecurityManager] provider implementation used to secure
 * Apache Geode.
 *
 * @author John Blum
 * @see SecurityManagerSupport
 * @see Role
 * @see User
 * @see SecurityRepository
 * @see ResourcePermission
 * @see org.apache.geode.security.SecurityManager
 * @see org.springframework.stereotype.Service
 * @since 1.0.0
 */
@Service
public class SimpleSecurityManager extends SecurityManagerSupport {

	protected SecurityRepository securityRepository;

	public SimpleSecurityManager(SecurityRepository securityRepository) {
		this.securityRepository = securityRepository;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Object authenticate(Properties securityProperties) {
		var username = getUsername(securityProperties);
		var password = getPassword(securityProperties);

		logDebug("User with name [{}] is attempting to login with password [{}]", username, password);

		var user = securityRepository.findBy(username);

		if (isNotAuthentic(user, password)) {
			throw new AuthenticationFailedException(String.format("Failed to authenticate user [%s]", username));
		}

		return user;
	}

	/* (non-Javadoc) */
	protected boolean isAuthentic(User user, String credentials) {
		return user != null && user.getCredentials().equals(credentials);
	}

	/* (non-Javadoc) */
	protected boolean isNotAuthentic(User user, String credentials) {
		return !isAuthentic(user, credentials);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean authorize(Object principal, ResourcePermission permission) {
		logDebug("Principal [{}] is requesting access to a Resource {} with the required Permission [{}]", principal,
				permission.getResource(), permission);

		return isAuthorized(principal, permission);
	}

	/* (non-Javadoc) */
	protected boolean isAuthorized(Object principal, ResourcePermission permission) {
		var user = resolveUser(principal);

		return user != null && isAuthorized(user, permission);
	}

	/* (non-Javadoc) */
	protected User resolveUser(Object principal) {
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return securityRepository.findBy(getName(principal));
		}
	}

	/* (non-Javadoc) */
	protected boolean isAuthorized(User user, ResourcePermission requiredPermission) {
		if (!user.hasPermission(requiredPermission)) {
			for (Role role : user.getRoles()) {
				for (ResourcePermission userPermission : role.getPermissions()) {
					if (isPermitted(userPermission, requiredPermission)) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	/* (non-Javadoc) */
	protected boolean isPermitted(ResourcePermission userPermission, ResourcePermission resourcePermission) {
		return userPermission.implies(resourcePermission);
	}

}
