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
package example.springdata.jdbc.mybatis;

import lombok.Data;

import org.springframework.data.annotation.Id;

/**
 * A manual instructing how to assemble a {@link LegoSet}.
 *
 * @author Jens Schauder
 */
@Data
public class Manual {

	private @Id Long id = null;
	private String author;
	private String text;

	Manual(String text, String author) {

		this.author = author;
		this.text = text;
	}
}
