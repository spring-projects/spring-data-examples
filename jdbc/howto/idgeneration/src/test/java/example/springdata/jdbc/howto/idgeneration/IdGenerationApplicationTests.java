/*
 * Copyright 2025-present the original author or authors.
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
package example.springdata.jdbc.howto.idgeneration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
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

		// Spring Data JDBC 4.x no longer throws IncorrectUpdateSemanticsDataAccessException
		// when saving an entity with a preset non-null ID. Instead it silently attempts an
		// UPDATE (which affects 0 rows) and returns without error. Use template.insert()
		// to explicitly insert a new aggregate with a user-supplied ID.
		//
		// The recommended workaround is to use template.insert() as shown in
		// insertNewAggregateWithPresetIdUsingTemplate(), or to implement Persistable
		// as shown in determineIsNewPerPersistable().
		Minion result = minions.save(before);

		// The save silently does an UPDATE (0 rows affected) and returns the entity unchanged.
		// The record is NOT actually persisted — verify it is absent from the database.
		assertThat(minions.findById(42L)).isEmpty();
		assertThat(result.id).isEqualTo(42L);
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
