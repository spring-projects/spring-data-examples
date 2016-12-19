/*
 * Copyright 2015 the original author or authors.
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

package example.springdata.neo4j;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

/**
 * An Actor node entity.
 *
 * @author Luanne Misquitta
 * @author Oliver Gierke
 * @author Mark Angrish
 */
@NodeEntity(label = "Actor")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public class Actor {

	private @GraphId Long id;

	@Convert(UuidStringConverter.class)
	@Index(unique = true, primary = true)
	private UUID uuid = UUID.randomUUID();

	private final String name;

	@Relationship(type = "ACTED_IN")
	private final  Set<Role> roles = new HashSet<>();

	public void actedIn(Movie movie, String roleName) {

		Role role = new Role(this, roleName, movie);

		roles.add(role);
		movie.getRoles().add(role);
	}
}
