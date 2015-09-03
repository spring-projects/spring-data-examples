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

package example.springdata.neo4j.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * An Actor node entity.
 *
 * @author Luanne Misquitta
 */
@NodeEntity(label = "Actor")
public class Actor {

	@GraphId private Long id;
	private String name;

	@Relationship(type = "ACTED_IN")
	private Set<Role> roles = new HashSet<>();

	public Actor() {
	}

	public Actor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void actedIn(Movie movie, String roleName) {

		Role role = new Role();
		role.setRole(roleName);
		role.setActor(this);
		role.setMovie(movie);
		roles.add(role);
		movie.getRoles().add(role);
	}
}
