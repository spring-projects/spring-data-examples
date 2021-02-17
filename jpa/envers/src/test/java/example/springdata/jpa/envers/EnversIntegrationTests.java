/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.jpa.envers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.RevisionMetadata.RevisionType;
import org.springframework.data.history.Revisions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Demonstrating the usage of Spring Data Envers
 * 
 * @author Jens Schauder
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EnversIntegrationTests {

	@Autowired PersonRepository repository;
	@Autowired TransactionTemplate tx;

	@Test
	void testRepository() {

		Person updated = preparePersonHistory();

		Revisions<Long, Person> revisions = repository.findRevisions(updated.id);

		assertThat(revisions) //
				.extracting( //
						rev -> rev.getEntity().name, //
						rev -> rev.getMetadata().getRevisionType()) //
				.containsExactly( //
						tuple("John", RevisionType.INSERT), //
						tuple("Jonny", RevisionType.UPDATE), //
						tuple(null, RevisionType.DELETE) //
				);
	}

	private Person preparePersonHistory() {

		Person john = new Person();
		john.setName("John");

		//create
		Person saved = tx.execute(__ -> repository.save(john));
		assertThat(saved).isNotNull();

		saved.setName("Jonny");

		// update
		Person updated = tx.execute(__ -> repository.save(saved));
		assertThat(updated).isNotNull();

		// delete
		tx.executeWithoutResult(__->repository.delete(updated));
		return updated;
	}
}
