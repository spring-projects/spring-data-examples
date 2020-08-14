/*
 * Copyright 2015-2018 the original author or authors.
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

package example.springdata.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * An Actor node entity.
 *
 * @author Luanne Misquitta
 * @author Oliver Gierke
 * @author Michael J. Simons
 */
@Node
public class Actor {

	private @Id @GeneratedValue Long id;
	private final String name;
	private @Relationship(type = "ACTED_IN") Map<Movie, Roles> roles = new HashMap<>();

	public Actor(String name) {
		this.name = name;
	}

	public void actedIn(Movie movie, String roleName) {

		Roles movieRoles = this.roles.computeIfAbsent(movie, m -> new Roles());
		movieRoles.addRole(roleName);
		movie.getActors().put(this, movieRoles);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Map<Movie, Roles> getRoles() {
		return roles;
	}

	public void setRoles(Map<Movie, Roles> roles) {
		this.roles = roles;
	}

	@Override public String toString() {
		return "Actor{" +
			"name='" + name + '\'' +
			'}';
	}
}
