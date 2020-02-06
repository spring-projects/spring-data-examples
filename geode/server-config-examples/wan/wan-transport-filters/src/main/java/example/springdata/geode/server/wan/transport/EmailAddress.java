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

package example.springdata.geode.server.wan.transport;

import java.io.Serializable;

/**
 * Value object to represent email addresses.
 *
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
public class EmailAddress implements Serializable {
	private String value;

	public EmailAddress(String value) {

		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean equals(Object other) {
		if (this != other) {
			if (other instanceof EmailAddress) {
				EmailAddress emailAddress = (EmailAddress) other;
				return this.value.equals(emailAddress.value);
			}
			return false;
		} else {
			return true;
		}
	}
}