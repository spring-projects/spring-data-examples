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
package example.springdata.jdbc.howto.idgeneration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@DataJdbcTest
class IdGenerationApplicationTests {

	@Autowired
	MinionRepository minions;

	@Autowired
	StringIdMinionRepository stringions;

	@Autowired
	VersionedMinionRepository versionedMinions;

	@Autowired
	PersistableMinionRepository persistableMinions;

	@Autowired
	JdbcAggregateTemplate template;

	@Test
	void saveWithNewIdFromDb() {

		Minion before = new Minion("Bob");
		assertThat(before.id).isNull();

		Minion after = minions.save(before);

		assertThat(after.id).isNotNull();
	}

	@Test
	void cantSaveNewAggregateWithPresetId() {

		Minion before = new Minion("Stuart");
		before.id = 42L;

		// We can't save this because Spring Data JDBC thinks it has to do an update.
		assertThatThrownBy(() -> minions.save(before)).isInstanceOf(IncorrectUpdateSemanticsDataAccessException.class);
	}

	@Test
	void insertNewAggregateWithPresetIdUsingTemplate() {

		Minion before = new Minion("Stuart");
		before.id = 42L;

		template.insert(before);

		Minion reloaded = minions.findById(42L).get();
		assertThat(reloaded.name).isEqualTo("Stuart");
	}

	@Test
	void idByCallBack() {

		StringIdMinion before = new StringIdMinion("Kevin");

		stringions.save(before);

		assertThat(before.id).isNotNull();

		StringIdMinion reloaded = stringions.findById(before.id).get();
		assertThat(reloaded.name).isEqualTo("Kevin");
	}

	@Test
	void determineIsNewPerVersion() {

		VersionedMinion before = new VersionedMinion(23L, "Bob");

		assertThat(before.id).isNotNull();

		versionedMinions.save(before);

		// It's saved!
		VersionedMinion reloaded = versionedMinions.findById(before.id).get();
		assertThat(reloaded.name).isEqualTo("Bob");
	}

	@Test
	void determineIsNewPerPersistable() {

		PersistableMinion before = new PersistableMinion(23L, "Dave");

		persistableMinions.save(before);

		// It's saved!
		PersistableMinion reloaded = persistableMinions.findById(before.id).get();
		assertThat(reloaded.name).isEqualTo("Dave");
	}
}
