/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.client.entityregion;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;

/**
 * A customer used for Lucene examples.
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */

@Region(name = "Customers")
public class Customer implements Serializable {

	@Id
	private Long id;
	private EmailAddress emailAddress;
	private String firstName;
	private String lastName;

	public Customer(Long id, EmailAddress emailAddress, String firstName, String lastName) {
		this.id = id;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public boolean equals(Object other) {
		if (this != other) {
			if (other instanceof Customer) {
				Customer customer = (Customer) other;
				return (this.id.equals(customer.id) && this.emailAddress.equals(customer.emailAddress) && this.firstName.equals(customer.firstName) && this.lastName.equals(customer.lastName));
			}
			return false;
		} else {
			return true;
		}
	}
}