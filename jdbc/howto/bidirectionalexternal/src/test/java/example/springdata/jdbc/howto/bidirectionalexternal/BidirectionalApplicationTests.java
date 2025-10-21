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
package example.springdata.jdbc.howto.bidirectionalexternal;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@DataJdbcTest
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
