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
package example.springdata.geode.server.storage;

import lombok.Data;

import java.io.Serializable;

/**
 * Value object to represent email addresses.
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Data
public class EmailAddress implements Serializable {

	private String value;

	public EmailAddress(String value) {

		this.value = value;
	}
}
