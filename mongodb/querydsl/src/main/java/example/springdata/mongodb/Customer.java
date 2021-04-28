/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

/**
 * An entity to represent a customer.
 *
 * @author Christoph Strobl
 */
@Document
public class Customer {

	private String id, firstname, lastname;

	/**
	 * Creates a new {@link Customer} with the given firstname and lastname.
	 *
	 * @param firstname must not be {@literal null} or empty.
	 * @param lastname must not be {@literal null} or empty.
	 */
	public Customer(String firstname, String lastname) {

		Assert.hasText(firstname, "Firstname must not be null or empty!");
		Assert.hasText(lastname, "Lastname must not be null or empty!");

		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "Customer{" + "id='" + id + '\'' + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\''
				+ '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		var customer = (Customer) o;

		if (id != null ? !id.equals(customer.id) : customer.id != null)
			return false;
		if (firstname != null ? !firstname.equals(customer.firstname) : customer.firstname != null)
			return false;
		return lastname != null ? lastname.equals(customer.lastname) : customer.lastname == null;
	}

	@Override
	public int hashCode() {
		var result = id != null ? id.hashCode() : 0;
		result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
		result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
		return result;
	}
}
