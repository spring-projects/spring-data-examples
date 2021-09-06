package example.springdata.jdbc.howto.idgeneration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@SpringBootTest
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
		assertThatThrownBy(() -> minions.save(before)).getRootCause().isInstanceOf(IncorrectUpdateSemanticsDataAccessException.class);
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
