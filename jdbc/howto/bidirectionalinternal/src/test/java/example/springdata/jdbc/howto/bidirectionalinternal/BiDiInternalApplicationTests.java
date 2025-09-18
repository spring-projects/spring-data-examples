/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.jdbc.howto.bidirectionalinternal;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
class BiDiInternalApplicationTests {

	@Autowired
	MinionRepository minions;

	@Test
	void biDi() {

		Minion bob = new Minion("Bob");
		bob.addToy(new Toy("Dolphin"));
		bob.addToy(new Toy("Tiger Duck"));

		minions.save(bob);

		Minion reloaded = minions.findById(bob.id).get();

		reloaded.showYourToys();
	}
}
