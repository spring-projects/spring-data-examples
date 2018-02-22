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
package example.springdata.jdbc.basics.aggregate;

import example.springdata.jdbc.basics.Output;

import java.time.Period;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Demonstrates various possibilities to customize the behavior of a repository.
 *
 * @author Jens Schauder
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AggregateConfiguration.class)
@AutoConfigureJdbc
public class AggregateTests {

	@Autowired LegoSetRepository repository;

	@Test
	public void exerciseSomewhatComplexEntity() {

		LegoSet smallCar = createLegoSet();
		smallCar.setManual(new Manual("Just put all the pieces together in the right order", "Jens Schauder"));
		smallCar.addModel("suv", "SUV with sliding doors.");
		smallCar.addModel("roadster", "Slick red roadster.");

		repository.save(smallCar);
		Output.list(repository.findAll(), "Original LegoSet");

		smallCar.getManual().setText("Just make it so it looks like a car.");
		smallCar.addModel("pickup", "A pickup truck with some tools in the back.");

		repository.save(smallCar);
		Output.list(repository.findAll(), "Updated");

		smallCar.setManual(new Manual("One last attempt: Just build a car! Ok?", "Jens Schauder"));

		repository.save(smallCar);
		Output.list(repository.findAll(), "Manual replaced");
	}

	private LegoSet createLegoSet() {

		LegoSet smallCar = new LegoSet();

		smallCar.setName("Small Car 01");
		smallCar.setMinimumAge(Period.ofYears(5));
		smallCar.setMaximumAge(Period.ofYears(12));

		return smallCar;
	}
}
