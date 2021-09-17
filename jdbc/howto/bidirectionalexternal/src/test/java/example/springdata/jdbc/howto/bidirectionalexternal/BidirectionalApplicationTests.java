package example.springdata.jdbc.howto.bidirectionalexternal;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
class BidirectionalApplicationTests {

	@Autowired
	MinionRepository minions;

	@Autowired
	PersonRepository persons;

	@Autowired
	JdbcAggregateTemplate template;

	@Test
	void bidi() {


		AggregateReference<Person, Long> scarletReference = AggregateReference.to(persons.save(new Person("Scarlet")).id);

		Minion bob = minions.save(new Minion("Bob", scarletReference));
		Minion kevin = minions.save(new Minion("Kevin", scarletReference));
		Minion stuart = minions.save(new Minion("Stuart", scarletReference));

		AggregateReference<Person, Long> gruReference = AggregateReference.to(persons.save(new Person("Gru")).id);
		Minion tim = minions.save(new Minion("Tim", gruReference));

		Collection<Minion> minionsOfScarlet = minions.findByEvilMaster(scarletReference.getId());

		assertThat(minionsOfScarlet).extracting(m -> m.name).containsExactlyInAnyOrder("Bob", "Kevin", "Stuart");
	}


}
