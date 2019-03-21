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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * A Role relationship entity between an actor and movie.
 *
 * @author Luanne Misquitta
 * @author Michael J. Simons
 */
@RelationshipEntity(type = "ACTED_IN")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
public class Role {

	private @Id @GeneratedValue Long id;
	private @StartNode Actor actor;
	private String role;
	private @EndNode Movie movie;

	Role(Actor actor, String role, Movie movie) {
		this.actor = actor;
		this.role = role;
		this.movie = movie;
	}
}
