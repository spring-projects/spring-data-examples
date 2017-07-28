/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.cassandra.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.SASI.StandardAnalyzed;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Sample user class.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Mark Paluch
 */
@Data
@NoArgsConstructor
@Table(value = "users")
public class User {

	@PrimaryKey("user_id") private Long id;

	/**
	 * Allows secondary index creation on startup.
	 */
	@Indexed private String username;

	@Column("fname") private String firstname;

	/**
	 * Allows SASI index creation on startup.
	 */
	@SASI @StandardAnalyzed private String lastname;

	private List<String> emailAddresses;

	public User(Long id) {
		this.setId(id);
	}
}
