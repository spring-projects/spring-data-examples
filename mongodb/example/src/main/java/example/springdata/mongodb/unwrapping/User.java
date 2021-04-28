/*
 * Copyright 2021-2021 the original author or authors.
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
package example.springdata.mongodb.unwrapping;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Unwrapped;
import org.springframework.data.mongodb.core.mapping.Unwrapped.OnEmpty;
import org.springframework.lang.Nullable;

/**
 * @author Christoph Strobl
 */
public class User {

	@Nullable
	private String id;

	@Nullable
	@Unwrapped(onEmpty = OnEmpty.USE_NULL)
	private UserName userName;

	/*
	 * The prefix allows to unwrap multiple properties of the same type assigning them a unique prefix.
	 * In this case the `Email.email` will be stored as `primary_email`.
	 */
	@Nullable
	@Unwrapped(onEmpty = OnEmpty.USE_NULL, prefix = "primary_")
	private Email email;

	User() {
	}

	public User(String id, UserName userName, Email email) {

		this.id = id;
		this.userName = userName;
		this.email = email;
	}

	User(String id, String userName, String email) {
		this(id, new UserName(userName), new Email(email));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserName getUserName() {
		return userName;
	}

	public Email getEmail() {
		return email;
	}

	public void setUserName(@Nullable UserName userName) {
		this.userName = userName;
	}

	public void setEmail(@Nullable Email email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		var user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(userName, user.userName) &&
				Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userName, email);
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", userName=" + userName +
				", email=" + email +
				'}';
	}
}
