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
package example.springdata.jdbc.jooq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrates simple CRUD operations with a simple entity without any references.
 *
 * @author Jens Schauder
 * @author Florian Lüdiger
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CategoryConfiguration.class)
@AutoConfigureJdbc
@ComponentScan
public class SimpleEntityTests {

	@Autowired
    CategoryRepository repository;

	@Test
	public void exerciseRepositoryForSimpleEntity() {

		// create some categories
		Category cars = new Category(null,"Cars", "Anything that has approximately 4 wheels", AgeGroup._3to8);

		Category buildings = new Category(null,"Buildings", null, AgeGroup._12andOlder);

		// save categories
		repository.saveAll(asList(cars, buildings));
		Output.list(repository.findAll(), "`Cars` and `Buildings` got saved");

		assertThat(cars.getId()).isNotNull();
		assertThat(buildings.getId()).isNotNull();

		// update one
		buildings.setDescription("Famous and impressive buildings incl. the 'bike shed'.");
		repository.save(buildings);
		Output.list(repository.findAll(), "`Buildings` has a description");

		List<Category> categoryList = repository.getCategoriesWithAgeGroup(AgeGroup._3to8);
		assertThat(categoryList.size()).isEqualTo(1);
		assertThat(categoryList.get(0).getName()).isEqualTo(cars.getName());
		assertThat(categoryList.get(0).getDescription()).isEqualTo(cars.getDescription());
		assertThat(categoryList.get(0).getAgeGroup()).isEqualTo(cars.getAgeGroup());
	}
}
