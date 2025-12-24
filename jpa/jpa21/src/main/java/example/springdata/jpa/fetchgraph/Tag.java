/*
 * Copyright 2015-2021 the original author or authors.
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
package example.springdata.jpa.fetchgraph;

import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Setter;

/**
 * @author Thomas Darimont
 */

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Tag {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String name;

	public Tag(String name) {
		this.name = name;
	}
	public static Tag createTestTag(int num){
		return new Tag(String.format("Tag%d", num));
	}
}
