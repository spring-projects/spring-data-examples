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

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

/**
 * {@link Neo4jRepository} for {@link Actor actors}.
 *
 * @author Luanne Misquitta
 * @author Michael J. Simons
 */
public interface ActorRepository extends Neo4jRepository<Actor, Long> {

	/**
	 * Custom query to navigate over relationships with properties.
	 *
	 * @param title
	 * @return
	 */
	@Query("MATCH (a:Actor) - [r:ACTED_IN] -> (m:Movie {title: $title}) RETURN a, collect(r), collect(m)")
	List<Actor> findAllByRolesMovieTitle(String title);
}
