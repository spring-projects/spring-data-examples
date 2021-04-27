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

import java.util.Properties;

import org.apache.geode.security.AuthenticationFailedException;
import org.apache.geode.security.ResourcePermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.support.LazyWiringDeclarableSupport;
import org.springframework.util.Assert;

/**
 * The {@link SecurityManagerProxy} class is a Proxy delegating to an underlying Apache Geode
 * {@link org.apache.geode.security.SecurityManager} implementation, that maybe a Spring managed bean in a Spring
 * context that may have been configured and auto-wired the Spring container, or possibly other managed environment
 * (Cloud or Java EE Server, etc).
 *
 * @author John Blum
 * @see org.apache.geode.security.SecurityManager
 * @see org.springframework.data.gemfire.support.LazyWiringDeclarableSupport
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class SecurityManagerProxy extends LazyWiringDeclarableSupport
		implements org.apache.geode.security.SecurityManager {

	private org.apache.geode.security.SecurityManager securityManager;

	/**
	 * Constructs an instance of the {@link SecurityManagerProxy}, whick will delegate all Apache Geode security
	 * operations to a Spring managed {@link org.apache.geode.security.SecurityManager} bean.
	 */
	public SecurityManagerProxy() {
		// TODO remove init() call when GEODE-2083 (https://issues.apache.org/jira/browse/GEODE-2083) is resolved!
		// NOTE the init(:Properties) call in the constructor is less than ideal since...
		// 1) it allows the *this* reference to escape, and...
		// 2) it is Geode's responsibility to identify Geode Declarable objects and invoke their init(:Properties) method
		// However, the init(:Properties) method invocation in the constructor is necessary to enable this Proxy to be
		// identified and auto-wired in a Spring context.
		init(new Properties());
	}

	/**
	 * Returns a reference to the Apache Geode {@link org.apache.geode.security.SecurityManager} instance delegated to by
	 * this {@link SecurityManagerProxy}.
	 *
	 * @return a reference to the underlying, Apache Geode {@link org.apache.geode.security.SecurityManager} instance
	 *         delegated to by this {@link SecurityManagerProxy}.
	 * @throws IllegalStateException if the configured Apache Geode {@link org.apache.geode.security.SecurityManager} was
	 *           not properly initialized.
	 * @see org.apache.geode.security.SecurityManager
	 */
	protected org.apache.geode.security.SecurityManager getSecurityManager() {
		Assert.state(this.securityManager != null, "SecurityManager was not properly initialized");
		return this.securityManager;
	}

	/**
	 * Sets a reference to the Apache Geode {@link org.apache.geode.security.SecurityManager} instance delegated to by
	 * this {@link SecurityManagerProxy}.
	 *
	 * @param securityManager reference to the underlying, Apache Geode {@link org.apache.geode.security.SecurityManager}
	 *          instance delegated to by this {@link SecurityManagerProxy}.
	 * @throws IllegalArgumentException if the Apache Geode {@link org.apache.geode.security.SecurityManager} reference is
	 *           {@literal null}.
	 * @see org.apache.geode.security.SecurityManager
	 */
	@Autowired
	public void setSecurityManager(org.apache.geode.security.SecurityManager securityManager) {
		Assert.notNull(securityManager, "SecurityManager must not be null");
		this.securityManager = securityManager;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Object authenticate(Properties properties) throws AuthenticationFailedException {
		return getSecurityManager().authenticate(properties);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean authorize(Object principal, ResourcePermission permission) {
		return getSecurityManager().authorize(principal, permission);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void close() {
		getSecurityManager().close();
	}
}
