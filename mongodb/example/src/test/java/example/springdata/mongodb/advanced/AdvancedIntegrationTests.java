/*
 * Copyright 2014-2021 the original author or authors.
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Meta;

import com.mongodb.BasicDBObject;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@DataMongoTest
class AdvancedIntegrationTests {

	@Autowired AdvancedRepository repository;
	@Autowired MongoOperations operations;

	private Customer dave, oliver, carter;

	@BeforeEach
	void setUp() {

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
	void findByFirstnameUsingMetaAttributes() {

		// execute derived finder method just to get the comment in the profile log
		repository.findByFirstname(dave.getFirstname());

		// execute another finder without meta attributes that should not be picked up
		repository.findByLastname(dave.getLastname(), Sort.by("firstname"));

		var cursor = operations.getCollection(ApplicationConfiguration.SYSTEM_PROFILE_DB)
				.find(new BasicDBObject("query.$comment", AdvancedRepository.META_COMMENT));

		for (var document : cursor) {

			var query = (Document) document.get("query");
			assertThat(query).containsKey("foo");
		}
	}
}
