/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.rest.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * Some convenient security utilities.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
class SecurityUtils {

	/**
	 * Configures the Spring Security {@link SecurityContext} to be authenticated as the user with the given username and
	 * password as well as the given granted authorities.
	 *
	 * @param username must not be {@literal null} or empty.
	 * @param password must not be {@literal null} or empty.
	 * @param roles
	 */
	public static void runAs(String username, String password, String... roles) {

		Assert.notNull(username, "Username must not be null!");
		Assert.notNull(password, "Password must not be null!");

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(roles)));
	}
}
