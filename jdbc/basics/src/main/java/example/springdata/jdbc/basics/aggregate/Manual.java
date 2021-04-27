/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.jdbc.basics.aggregate;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A manual instructing how to assemble a {@link LegoSet}.
 *
 * @author Jens Schauder
 */
@Data
@Table("HANDBUCH")
public class Manual {

	@Column("HANDBUCH_ID")
	private @Id Long id;
	private String author, text;

	Manual(String text, String author) {

		this.id = null;
		this.author = author;
		this.text = text;
	}
}
