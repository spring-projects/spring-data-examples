/*
 * Copyright 2017-2018 the original author or authors.
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
package example.springdata.jdbc.basics.simpleentity;

import static org.assertj.core.api.Assertions.*;

import example.springdata.jdbc.basics.Output;
import example.springdata.jdbc.basics.aggregate.AgeGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Demonstrates simple CRUD operations with a simple entity without any references.
 *
 * @author Jens Schauder
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CategoryConfiguration.class)
@AutoConfigureJdbc
public class SimpleEntityTests {

	@Autowired CategoryRepository repository;

	@Test
	public void exerciseRepositoryForSimpleEntity() {

		// create some categories
		Category cars = repository.save(new Category("Cars", "Anything that has approximately 4 wheels", AgeGroup._3to8));
		Category buildings = repository.save(new Category("Buildings", null, AgeGroup._12andOlder));

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
}
