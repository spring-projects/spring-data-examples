/*
 * Copyright 2023 the original author or authors.
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
package example.springdata.jdbc.singlequeryloading;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.relational.core.query.Criteria.*;
import static org.springframework.data.relational.core.query.Query.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

/**
 * Run tests demonstrating the use of Single Query Loading. You'll have to observe the executed queries.
 *
 * @author Jens Schauder
 */
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SingleQueryLoadingApplicationTests {

	@Autowired PetOwnerRepository petOwners;
	@Autowired JdbcAggregateTemplate template;

	private PetOwner emil;
	private PetOwner marry;

	@BeforeEach
	void setup() {

		petOwners.deleteAll();

		emil = petOwners.save(new PetOwner("Emil", //
				List.of(new Cat("Edgar"), new Cat("Einstein"), new Cat("Elliot"), new Cat("Elton"), new Cat("Evan")), //
				List.of(new Dog("Eric"), new Dog("Eddie"), new Dog("Eke"), new Dog("Echo")), //
				List.of(new Fish("Floaty Mc Floatface")) //
		));

		marry = petOwners.save(new PetOwner("Marry", List.of(new Cat("Mars"), new Cat("Maverick"), new Cat("Max")), //
				List.of(new Dog("Molly"), new Dog("Murphy"), new Dog("Madison"), new Dog("Macie")), //
				List.of(new Fish("Mahi Mahi"), new Fish("Mr. Limpet")) //
		));
	}

	@Test
	void loadById() {

		PetOwner emilReloaded = petOwners.findById(emil.Id).orElseThrow();

		assertThat(emilReloaded).isEqualTo(emil);
	}

	@Test
	void loadByNameUsingTemplate() {

		List<PetOwner> marries = (List<PetOwner>) template.findAll(query(where("name").is("Marry")), PetOwner.class);

		assertThat(marries).containsExactly(marry);
	}

}
