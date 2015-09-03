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
 * A Movie node entity.
 *
 * @author Luanne Misquitta
 */
@NodeEntity(label = "Movie")
public class Movie {

	@GraphId private Long id;
	private String title;

	@Relationship(type = "ACTED_IN", direction = "INCOMING")
	private Set<Role> roles = new HashSet<>();

	public Movie() {
	}

	public Movie(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Relationship(type = "ACTED_IN", direction = "INCOMING")
	public Set<Role> getRoles() {
		return roles;
	}

}
