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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * An Actor node entity.
 * Defining a node entity without the <code>label</code> attribute will result in a label
 * of "Actor" anyway. We use it explicitly here to show you how to change that.
 *
 * @author Luanne Misquitta
 * @author Oliver Gierke
 * @author Mark Angrish
 */
@NodeEntity(label = "Actor")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Actor {

	/**
	 * All domain objects currently require a field defined with `@GraphId`.
	 * It is an internal identifier used by Neo4j. It is not guaranteed to be the same value
	 * across database restarts and hence it is not recommended  be used as a unique identifier.
	 */
	private @GraphId Long graphId;

	/**
	 * Marking a field with an Index that is both <code>unique</code> and <code>primary</code> will
	 * allow this field to be used as a "primary" identifier. Only one primary identifier may exist in
	 * a hierarchy.
	 */
	@Getter
	@Index(unique = true, primary = true)
	private String id = UUID.randomUUID().toString();

	@Getter
	private final String name;

	@Getter
	@Relationship(type = "ACTED_IN")
	private final Set<Role> roles = new HashSet<>();

	public void actedIn(Movie movie, String roleName) {

		Role role = new Role(this, roleName, movie);

		roles.add(role);
		movie.getRoles().add(role);
	}
}
