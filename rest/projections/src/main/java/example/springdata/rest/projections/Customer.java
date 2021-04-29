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
package example.springdata.rest.projections;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author Oliver Gierke
 */
@Entity
@Data
@RequiredArgsConstructor
public class Customer {

	private @GeneratedValue @Id Long id;
	private final String firstname, lastname;
	private final Gender gender;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) //
	private final Address address;

	Customer() {
		this.firstname = null;
		this.lastname = null;
		this.address = null;
		this.gender = null;
	}

	enum Gender {
		MALE, FEMALE
	}
}
