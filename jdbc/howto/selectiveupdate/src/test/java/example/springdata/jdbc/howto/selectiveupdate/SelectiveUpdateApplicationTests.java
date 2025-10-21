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
package example.springdata.jdbc.howto.selectiveupdate;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests demonstrating various was for partial updates of an aggregate.
 */
@DataJdbcTest
class SelectiveUpdateApplicationTests {

	@Autowired MinionRepository minions;
	@Autowired PlainMinionRepository plainMinions;

	@Test
	void renameWithReducedView() {

		Minion bob = new Minion("Bob").addToy(new Toy("Tiger Duck")).addToy(new Toy("Security blanket"));
		minions.save(bob);

		PlainMinion plainBob = plainMinions.findById(bob.id).orElseThrow();
		plainBob.name = "Bob II.";
		plainMinions.save(plainBob);

		Minion bob2 = minions.findById(bob.id).orElseThrow();

		assertThat(bob2.toys).containsExactly(bob.toys.toArray(new Toy[] {}));
		assertThat(bob2.name).isEqualTo("Bob II.");
		assertThat(bob2.color).isEqualTo(Color.YELLOW);
	}

	@Test
	void turnPurpleByDirectUpdate() {

		Minion bob = new Minion("Bob").addToy(new Toy("Tiger Duck")).addToy(new Toy("Security blanket"));
		minions.save(bob);

		minions.turnPurple(bob.id);

		Minion bob2 = minions.findById(bob.id).orElseThrow();

		assertThat(bob2.toys).containsExactlyElementsOf(bob.toys);
		assertThat(bob2.name).isEqualTo("Bob");
		assertThat(bob2.color).isEqualTo(Color.PURPLE);
	}

	@Test
	void grantPartyHat() {

		Minion bob = new Minion("Bob").addToy(new Toy("Tiger Duck")).addToy(new Toy("Security blanket"));
		minions.save(bob);

		minions.addPartyHat(bob);

		Minion bob2 = minions.findById(bob.id).orElseThrow();

		assertThat(bob2.toys).extracting("name").containsExactlyInAnyOrder("Tiger Duck", "Security blanket", "Party Hat");
		assertThat(bob2.name).isEqualTo("Bob");
		assertThat(bob2.color).isEqualTo(Color.YELLOW);
		assertThat(bob2.version).isEqualTo(bob.version + 1);

		assertThatExceptionOfType(OptimisticLockingFailureException.class).isThrownBy(() -> minions.addPartyHat(bob));
	}

	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void cannotGrantPartyHatWhenOutOfSync() {

		Minion bob = new Minion("Bob").addToy(new Toy("Tiger Duck")).addToy(new Toy("Security blanket"));
		minions.save(bob);
		minions.turnPurple(bob.id);

		assertThat(bob.color).isEqualTo(Color.YELLOW);
		assertThat(bob.version).isOne();
		assertThatExceptionOfType(OptimisticLockingFailureException.class).isThrownBy(() -> minions.addPartyHat(bob));

		Minion bob2 = minions.findById(bob.id).orElseThrow();

		assertThat(bob2.name).isEqualTo("Bob");
		assertThat(bob2.color).isEqualTo(Color.PURPLE);
		assertThat(bob2.version).isEqualTo(bob.version + 1);
		assertThat(bob2.toys).extracting("name").containsExactlyInAnyOrder("Tiger Duck", "Security blanket");
	}

}
