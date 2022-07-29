/*
 * Copyright 2022 the original author or authors.
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
package example.springdata.jpa.hibernatemultitenant.partition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Persons extends JpaRepository<Person, Long> {

	static Person named(String name) {
		Person person = new Person();
		person.setName(name);
		return person;
	}

	@Query("select p from Person p where name = :name")
	Person findJpqlByName(String name);

	@Query(value = "select * from Person p where name = :name", nativeQuery = true)
	Person findSqlByName(String name);
}
