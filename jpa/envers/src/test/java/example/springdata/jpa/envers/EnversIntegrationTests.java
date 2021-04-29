/*
 * Copyright 2021-2021 the original author or authors.
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

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata.RevisionType;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Demonstrating the usage of Spring Data Envers
 *
 * @author Jens Schauder
 */
@SpringBootTest
class EnversIntegrationTests {

	@Autowired PersonRepository repository;
	@Autowired TransactionTemplate tx;

	@Test
	void testRepository() {

		var updated = preparePersonHistory();

		var revisions = repository.findRevisions(updated.id);

		var revisionIterator = revisions.iterator();

		checkNextRevision(revisionIterator, "John", RevisionType.INSERT);
		checkNextRevision(revisionIterator, "Jonny", RevisionType.UPDATE);
		checkNextRevision(revisionIterator, null, RevisionType.DELETE);
		assertThat(revisionIterator.hasNext()).isFalse();

	}

	private void checkNextRevision(Iterator<Revision<Long, Person>> revisionIterator, String name,
			RevisionType revisionType) {

		assertThat(revisionIterator.hasNext()).isTrue();
		var revision = revisionIterator.next();
		assertThat(revision.getEntity().name).isEqualTo(name);
		assertThat(revision.getMetadata().getRevisionType()).isEqualTo(revisionType);
	}

	private Person preparePersonHistory() {

		var john = new Person();
		john.setName("John");

		// create
		var saved = tx.execute(__ -> repository.save(john));
		assertThat(saved).isNotNull();

		saved.setName("Jonny");

		// update
		var updated = tx.execute(__ -> repository.save(saved));
		assertThat(updated).isNotNull();

		// delete
		tx.executeWithoutResult(__ -> repository.delete(updated));
		return updated;
	}
}
