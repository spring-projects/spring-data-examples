/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.redis.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * @author Christoph Strobl
 * @author Mark Paluch
 */
interface PersonRepository extends CrudRepository<Person, String>, QueryByExampleExecutor<Person> {

	List<Person> findByLastname(String lastname);

	Page<Person> findPersonByLastname(String lastname, Pageable page);

	List<Person> findByFirstnameAndLastname(String firstname, String lastname);

	List<Person> findByFirstnameOrLastname(String firstname, String lastname);

	List<Person> findByAddress_City(String city);

	List<Person> findByAddress_LocationWithin(Circle circle);
}
