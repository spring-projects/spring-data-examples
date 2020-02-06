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

package example.springdata.geode.server.expiration.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.expiration.IdleTimeoutExpiration;
import org.springframework.data.gemfire.expiration.TimeToLiveExpiration;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Region(name = "Customers")
@IdleTimeoutExpiration(action = "DESTROY", timeout = "2")
@TimeToLiveExpiration(action = "DESTROY", timeout = "4")
public class Customer implements Serializable {
	@Id
	private Long id;
	private EmailAddress emailAddress;
	private String firstName;
	private String lastName;
	private List<Address> addresses = new ArrayList<>();

	public Customer(Long id, EmailAddress emailAddress, String firstName, String lastName, Address... addresses) {
		this.id = id;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses.addAll(Arrays.asList(addresses));
	}

	public void add(Address address) {
		addresses.add(address);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}