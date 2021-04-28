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
package example.springdata.jdbc.basics.simpleentity;

import static org.assertj.core.api.Assertions.*;

import example.springdata.jdbc.basics.Output;
import example.springdata.jdbc.basics.aggregate.AgeGroup;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Demonstrates simple CRUD operations with a simple entity without any references.
 *
 * @author Jens Schauder
 * @author Divya Srivastava
 */
@SpringBootTest(classes = CategoryConfiguration.class)
@AutoConfigureJdbc
class SimpleEntityTests {

	@Autowired CategoryRepository repository;

	@Test
	void exerciseRepositoryForSimpleEntity() {

		// create some categories
		var cars = repository.save(new Category("Cars", "Anything that has approximately 4 wheels.", AgeGroup._3to8));
		var buildings = repository.save(new Category("Buildings", null, AgeGroup._12andOlder));

		// save categories
		Output.list(repository.findAll(), "`Cars` and `Buildings` got saved");

		assertThat(cars.getId()).isNotNull();
		assertThat(buildings.getId()).isNotNull();

		// update one
		buildings.setDescription("Famous and impressive buildings incl. the 'bike shed'.");
		repository.save(buildings);
		Output.list(repository.findAll(), "`Buildings` has a description");

		// delete stuff again
		repository.delete(cars);
		Output.list(repository.findAll(), "`Cars` is gone.");
	}

	@Test
	void directInsert() {

		var cars = new Category("Cars", "Anything that has approximately 4 wheels.", AgeGroup._3to8).withId(23L);
		repository.insert(cars);

		Output.list(repository.findAll(), "`Cars` inserted with id 23L");
	}
}
