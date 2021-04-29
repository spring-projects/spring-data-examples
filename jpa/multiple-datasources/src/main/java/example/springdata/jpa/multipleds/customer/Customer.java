/*
 * Copyright 2015-2021 the original author or authors.
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
package example.springdata.jpa.multipleds.customer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Simple domain class representing a {@link Customer}.
 *
 * @author Oliver Gierke
 */
@Entity
@EqualsAndHashCode(of = "id")
@Getter
@RequiredArgsConstructor
@ToString
public class Customer {

	private @Id @GeneratedValue Long id;
	private final String firstname, lastname;

	Customer() {
		this.firstname = null;
		this.lastname = null;
	}

	public CustomerId getId() {
		return new CustomerId(id);
	}

	@Value
	@Embeddable
	@RequiredArgsConstructor
	@SuppressWarnings("serial")
	public static class CustomerId implements Serializable {

		private final Long customerId;

		CustomerId() {
			this.customerId = null;
		}
	}
}
