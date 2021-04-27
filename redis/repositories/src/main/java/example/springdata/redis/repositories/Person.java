/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.redis.repositories;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * {@link Person} object stored inside a Redis {@literal HASH}. <br />
 * <br />
 * Sample (key = persons:9b0ed8ee-14be-46ec-b5fa-79570aadb91d):
 *
 * <pre>
 * <code>
 * _class := example.springdata.redis.domain.Person
 * id := 9b0ed8ee-14be-46ec-b5fa-79570aadb91d
 * firstname := eddard
 * lastname := stark
 * gender := MALE
 * address.city := winterfell
 * address.country := the north
 * children.[0] := persons:41436096-aabe-42fa-bd5a-9a517fbf0260
 * children.[1] := persons:1973d8e7-fbd4-4f93-abab-a2e3a00b3f53
 * children.[2] := persons:440b24c6-ede2-495a-b765-2d8b8d6e3995
 * children.[3] := persons:85f0c1d1-cef6-40a4-b969-758ebb68dd7b
 * children.[4] := persons:73cb36e8-add9-4ec0-b5dd-d820e04f44f0
 * children.[5] := persons:9c2461aa-2ef2-469f-83a2-bd216df8357f
 * </code>
 * </pre>
 *
 * @author Christoph Strobl
 */
@Data
@EqualsAndHashCode(exclude = { "children" })
@RedisHash("persons")
@NoArgsConstructor
class Person {

	/**
	 * The {@literal id} and {@link RedisHash#toString()} build up the {@literal key} for the Redis {@literal HASH}.
	 * <br />
	 *
	 * <pre>
	 * <code>
	 * {@link RedisHash#value()} + ":" + {@link Person#id}
	 * //eg. persons:9b0ed8ee-14be-46ec-b5fa-79570aadb91d
	 * </code>
	 * </pre>
	 *
	 * <strong>Note:</strong> empty {@literal id} fields are automatically assigned during save operation.
	 */
	private @Id String id;

	/**
	 * Using {@link Indexed} marks the property as for indexing which uses Redis {@literal SET} to keep track of
	 * {@literal ids} for objects with matching values. <br />
	 *
	 * <pre>
	 * <code>
	 * {@link RedisHash#value()} + ":" + {@link Field#getName()} +":" + {@link Field#get(Object)}
	 * //eg. persons:firstname:eddard
	 * </code>
	 * </pre>
	 */
	private @Indexed String firstname;
	private @Indexed String lastname;

	private Gender gender;

	/**
	 * Since {@link Indexed} is used on {@link Address#getCity()} index structures for {@code persons:address:city} are be
	 * maintained.
	 */
	private Address address;

	/**
	 * Using {@link Reference} allows to link to existing objects via their {@literal key}. The values stored in the
	 * objects {@literal HASH} looks like:
	 *
	 * <pre>
	 * <code>
	 * children.[0] := persons:41436096-aabe-42fa-bd5a-9a517fbf0260
	 * children.[1] := persons:1973d8e7-fbd4-4f93-abab-a2e3a00b3f53
	 * children.[2] := persons:440b24c6-ede2-495a-b765-2d8b8d6e3995
	 * </code>
	 * </pre>
	 */
	private @Reference List<Person> children;

	public Person(String firstname, String lastname, Gender gender) {

		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
	}
}
