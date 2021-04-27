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
package example.springdata.mongodb.fluent;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Christoph Strobl
 */
@Document(collection = "star-wars")
@Getter
@Setter
@EqualsAndHashCode(of = "name")
class SWCharacter {

	private @Id String id;
	private @Field("firstname") String name;
	private Planet homePlanet;

	SWCharacter(String name) {
		this.name = name;
	}
}
