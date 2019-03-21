/*
 * Copyright 2014-2018 the original author or authors.
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
package example.springdata.mongodb.advanced;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.customer.Customer;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Meta;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvancedIntegrationTests {

	@Autowired AdvancedRepository repository;
	@Autowired MongoOperations operations;

	Customer dave, oliver, carter;

	@Before
	public void setUp() {

		repository.deleteAll();

		dave = repository.save(new Customer("Dave", "Matthews"));
		oliver = repository.save(new Customer("Oliver August", "Matthews"));
		carter = repository.save(new Customer("Carter", "Beauford"));
	}

	/**
	 * This test demonstrates usage of {@code $comment} {@link Meta} usage. One can also enable profiling using
	 * {@code --profile=2} when starting {@literal mongod}.
	 * <p>
	 * <strong>NOTE</strong>: Requires MongoDB v. 2.6.4+
	 */
	@Test
	public void findByFirstnameUsingMetaAttributes() {

		// execute derived finder method just to get the comment in the profile log
		repository.findByFirstname(dave.getFirstname());

		// execute another finder without meta attributes that should not be picked up
		repository.findByLastname(dave.getLastname(), Sort.by("firstname"));

		FindIterable<Document> cursor = operations.getCollection(ApplicationConfiguration.SYSTEM_PROFILE_DB)
				.find(new BasicDBObject("query.$comment", AdvancedRepository.META_COMMENT));

		for (Document document : cursor) {

			Document query = (Document) document.get("query");
			assertThat(query).containsKey("foo");
		}
	}
}
