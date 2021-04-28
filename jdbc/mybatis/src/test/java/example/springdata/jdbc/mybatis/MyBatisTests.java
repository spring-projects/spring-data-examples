/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.jdbc.mybatis;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Demonstrates queries can be mapped using MyBatis.
 *
 * @author Jens Schauder
 * @author Divya Srivastava
 */
@SpringBootTest(classes = MyBatisConfiguration.class)
@AutoConfigureJdbc
@MybatisTest
class MyBatisTests {

	@Autowired LegoSetRepository repository;

	@Test
	void exerciseSomewhatComplexEntity() {

		var smallCar = createLegoSet();

		smallCar.setManual(new Manual("Just put all the pieces together in the right order", "Jens Schauder"));
		smallCar.addModel("suv", "SUV with sliding doors.");
		smallCar.addModel("roadster", "Slick red roadster.");

		repository.save(smallCar);

		assertThat(smallCar.getId()).isNotNull();
		assertThat(repository.findById(smallCar.getId()).get().getModels()).hasSize(2);

		Output.list(repository.findAll(), "Original LegoSet");

		smallCar.getManual().setText("Just make it so it looks like a car.");
		smallCar.addModel("pickup", "A pickup truck with some tools in the back.");

		repository.save(smallCar);

		Output.list(repository.findAll(), "Updated");

		smallCar.setManual(new Manual("One last attempt: Just build a car! Ok?", "Jens Schauder"));

		repository.save(smallCar);

		Output.list(repository.findAll(), "Manual replaced");
	}

	private static LegoSet createLegoSet() {

		var smallCar = new LegoSet();
		smallCar.setName("Small Car 01");
		return smallCar;
	}
}
