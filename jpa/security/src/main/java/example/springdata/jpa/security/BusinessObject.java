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
package example.springdata.jpa.security;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Thomas Darimont
 * @author Oliver Gierke
 */
@Entity
@Data
public class BusinessObject {

	private @Id @GeneratedValue @Getter Long id;
	private @ManyToOne User lastModifiedBy;
	private Date lastModifiedDate;

	private final String data;
	private final @ManyToOne User owner;

	public BusinessObject(String data, User owner) {

		this.data = data;
		this.owner = owner;
	}

	@SuppressWarnings("unused")
	private BusinessObject() {
		this.data = null;
		this.owner = null;
	}
}
