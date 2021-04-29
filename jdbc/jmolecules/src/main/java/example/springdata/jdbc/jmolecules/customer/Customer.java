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
package example.springdata.jdbc.jmolecules.customer;

import example.springdata.jdbc.jmolecules.customer.Customer.CustomerId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.util.Assert;

/**
 * @author Oliver Drotbohm
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class Customer implements AggregateRoot<Customer, CustomerId> {

	private final CustomerId id;
	private @Setter String firstname, lastname;
	private final List<Address> addresses;

	public Customer(String firstname, String lastname, Address address) {

		Assert.notNull(address, "Address must not be null!");

		this.id = new CustomerId(UUID.randomUUID());

		this.firstname = firstname;
		this.lastname = lastname;

		this.addresses = new ArrayList<>();
		this.addresses.add(address);
	}

	public record CustomerId(UUID id) implements Identifier {}
}
