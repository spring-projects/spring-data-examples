/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.jdbc.basics.aggregate;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * A repository for {@link LegoSet}.
 *
 * @author Jens Schauder
 */
interface LegoSetRepository extends CrudRepository<LegoSet, Integer> {

	@Query("""
			SELECT m.name model_name, m.description, l.name set_name
			  FROM model m
			  JOIN lego_set l
			  ON m.lego_set = l.id
			  WHERE :age BETWEEN l.min_age and l.max_age
			""")
	List<ModelReport> reportModelForAge(@Param("age") int age);

	/**
	 * See https://stackoverflow.com/questions/52978700/how-to-write-a-custom-query-in-spring-data-jdbc
	 * @param name
	 * @return
	 */
	@Query("""
			select a.*, b.handbuch_id as manual_handbuch_id, b.author as manual_author, b.text as manual_text from lego_set a
			join handbuch b on a.id = b.handbuch_id
			where a.name = :name
			""")
	List<LegoSet> findByName(@Param("name") String name);

	@Modifying
	@Query("UPDATE model set name = lower(name) WHERE name <> lower(name)")
	int lowerCaseMapKeys();
}
