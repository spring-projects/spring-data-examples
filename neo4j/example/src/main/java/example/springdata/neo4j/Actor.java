/*
 * Copyright 2015-2018 the original author or authors.
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

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * An Actor node entity.
 *
 * @author Luanne Misquitta
 * @author Oliver Gierke
 * @author Michael J. Simons
 */
@NodeEntity(label = "Actor")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
public class Actor {

	private @Id @GeneratedValue Long id;
	private String name;
	private @Relationship(type = "ACTED_IN") Set<Role> roles = new HashSet<>();

	public Actor(String name) {
		this.name = name;
	}

	public void actedIn(Movie movie, String roleName) {

		Role role = new Role(this, roleName, movie);

		roles.add(role);
		movie.getRoles().add(role);
	}
}
