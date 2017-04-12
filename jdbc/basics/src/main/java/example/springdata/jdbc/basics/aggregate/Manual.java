/*
 * Copyright 2017 the original author or authors.
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
package example.springdata.jdbc.basics.aggregate;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * A manual instructing how to assemble a {@link LegoSet}.
 *
 * @author Jens Schauder
 */
@Data
public class Manual {


	Manual(String text, String author) {

		this.id = null;
		this.author = author;
		this.text = text;
	}

	@Id
	private Long id;

	private String author;
	private String text;
}
