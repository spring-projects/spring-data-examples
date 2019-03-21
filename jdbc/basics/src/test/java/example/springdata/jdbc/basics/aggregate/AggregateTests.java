/*
 * Copyright 2017-2018 the original author or authors.
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

import static org.assertj.core.api.Assertions.*;

import example.springdata.jdbc.basics.Output;

import java.time.Period;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Demonstrates various possibilities to customize the behavior of a repository.
 *
 * @author Jens Schauder
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AggregateConfiguration.class)
@AutoConfigureJdbc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AggregateTests {

	@Autowired LegoSetRepository repository;

	@Test
	public void exerciseSomewhatComplexEntity() {

		LegoSet smallCar = createLegoSet("Small Car 01", 5, 12);
		smallCar.setManual(new Manual("Just put all the pieces together in the right order", "Jens Schauder"));
		smallCar.addModel("suv", "SUV with sliding doors.");
		smallCar.addModel("roadster", "Slick red roadster.");

		repository.save(smallCar);
		Iterable<LegoSet> legoSets = repository.findAll();
		Output.list(legoSets, "Original LegoSet");
		checkLegoSets(legoSets, "Just put all the pieces together in the right order", 2);

		smallCar.getManual().setText("Just make it so it looks like a car.");
		smallCar.addModel("pickup", "A pickup truck with some tools in the back.");

		repository.save(smallCar);
		legoSets = repository.findAll();
		Output.list(legoSets, "Updated");
		checkLegoSets(legoSets, "Just make it so it looks like a car.", 3);

		smallCar.setManual(new Manual("One last attempt: Just build a car! Ok?", "Jens Schauder"));

		repository.save(smallCar);
		legoSets = repository.findAll();
		Output.list(legoSets, "Manual replaced");
		checkLegoSets(legoSets, "One last attempt: Just build a car! Ok?", 3);

	}

	@Test
	public void customQueries() {

		LegoSet smallCars = createLegoSet("Small Car - 01", 5, 10);
		smallCars.setManual(new Manual("Just put all the pieces together in the right order", "Jens Schauder"));

		smallCars.addModel("SUV", "SUV with sliding doors.");
		smallCars.addModel("roadster", "Slick red roadster.");

		LegoSet f1Racer = createLegoSet("F1 Racer", 6, 15);
		f1Racer.setManual(new Manual("Build a helicopter or a plane", "M. Shoemaker"));

		f1Racer.addModel("F1 Ferrari 2018", "A very fast red car.");

		LegoSet constructionVehicles = createLegoSet("Construction Vehicles", 3, 6);
		constructionVehicles.setManual(
				new Manual("Build a Road Roler, a Mobile Crane, a Tracked Dumper, or a Backhoe Loader ", "Bob the Builder"));

		constructionVehicles.addModel("scoop", "A backhoe loader");
		constructionVehicles.addModel("Muck", "Muck is a continuous tracked dump truck with an added bulldozer blade");
		constructionVehicles.addModel("lofty", "A mobile crane");
		constructionVehicles.addModel("roley",
				"A road roller that loves to make up songs and frequently spins his eyes when he is excited.");

		repository.saveAll(Arrays.asList(smallCars, f1Racer, constructionVehicles));

		List<ModelReport> report = repository.reportModelForAge(6);
		Output.list(report, "Model Report");

		assertThat(report).hasSize(7)
				.allMatch(m -> m.getDescription() != null && m.getModelName() != null && m.getSetName() != null);

		int updated = repository.lowerCaseMapKeys();
		// SUV, F1 Ferrari 2018 and Muck get updated
		assertThat(updated).isEqualTo(3);

	}

	private LegoSet createLegoSet(String name, int minimumAge, int maximumAge) {

		LegoSet smallCar = new LegoSet();

		smallCar.setName(name);
		smallCar.setMinimumAge(Period.ofYears(minimumAge));
		smallCar.setMaximumAge(Period.ofYears(maximumAge));

		return smallCar;
	}

	private void checkLegoSets(Iterable<LegoSet> legoSets, String manualText, int numberOfModels) {

		assertThat(legoSets) //
				.extracting( //
						ls -> ls.getManual().getText(), //
						ls -> ls.getModels().size()) //
				.containsExactly(new Tuple(manualText, numberOfModels));
	}
}
