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
package example.springdata.neo4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * A Role relationship entity between an actor and movie.
 *
 * @author Luanne Misquitta
 * @author Michael J. Simons
 */
@RelationshipProperties
public class Roles {

	@Id
	@GeneratedValue
	private Long id;

	private final List<String> roles;

	@TargetNode
	private Movie movie;

	public Roles() {
		this(Collections.emptyList());
	}

	@PersistenceConstructor
	public Roles(List<String> roles) {
		this.roles = new ArrayList<>(roles);
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void addRole(String role) {
		if (!this.roles.contains(role)) {
			roles.add(role);
		}
	}
}
