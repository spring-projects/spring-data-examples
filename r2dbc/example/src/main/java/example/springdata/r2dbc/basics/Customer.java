/*
 * Copyright 2018-2021 the original author or authors.
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
package example.springdata.r2dbc.basics;

import java.util.Objects;

import org.springframework.data.annotation.Id;

/**
 * @author Oliver Gierke
 */
record Customer(@Id Integer id, String firstname, String lastname) {

	boolean hasId() {
		return id != null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Customer customer)) {
			return false;
		}
		return Objects.equals(firstname, customer.firstname) && Objects.equals(lastname, customer.lastname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstname, lastname);
	}
}
