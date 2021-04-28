/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.cassandra.streamoptional;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * Repository to manage {@link Person} instances.
 *
 * @author Mark Paluch
 */
public interface PersonRepository extends Repository<Person, String> {

	Optional<Person> findById(String id);

	Stream<Person> findAll();

	/**
	 * Sample method to derive a query from using JDK 8's {@link Optional} as return type.
	 *
	 * @param id
	 * @return
	 */
	@Query("select * from person where id = ?0")
	Optional<Person> findPersonById(String id);

	/**
	 * Sample default method to show JDK 8 feature support.
	 *
	 * @param person
	 * @return
	 */
	default Optional<Person> findByPerson(Person person) {
		return findPersonById(person == null ? null : person.id);
	}

	void deleteAll();

	Person save(Person person);
}
