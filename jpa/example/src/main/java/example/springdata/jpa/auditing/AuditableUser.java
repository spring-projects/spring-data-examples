/*
 * Copyright 2013-2014 the original author or authors.
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
package example.springdata.jpa.auditing;

import javax.persistence.Entity;

import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.AbstractAuditable;

/**
 * User domain class that uses auditing functionality of Spring Data that can either be aquired implementing
 * {@link Auditable} or extend {@link AbstractAuditable}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Entity
public class AuditableUser extends AbstractAuditable<AuditableUser, Long> {

	private static final long serialVersionUID = 1L;

	private String username;

	/**
	 * Set's the user's name.
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the user's name.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
}
