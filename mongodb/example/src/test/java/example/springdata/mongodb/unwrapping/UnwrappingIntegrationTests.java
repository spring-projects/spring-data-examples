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
package example.springdata.mongodb.unwrapping;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.client.model.Filters;

/**
 * Integration tests showing unwrapped/embedded document usage.
 *
 * @author Christoph Strobl
 */
@DataMongoTest
class UnwrappingIntegrationTests {

	@Autowired UserRepository repository;
	@Autowired MongoOperations operations;

	User jane = new User("jane-01", new UserName("jane-villanueva"), new Email("jane@home.com"));
	User rogelio = new User("rogelio-01", new UserName("rogelio-de-la-vega"), new Email("me@RogelioDeLaVega.com"));

	@BeforeEach
	void beforeEach() {

		operations.dropCollection(User.class);
		repository.saveAll(Arrays.asList(jane, rogelio));
	}

	@Test
	void documentStructure() {

		var stored = operations.execute(User.class, collection -> {
			return collection.find(Filters.eq("_id", rogelio.getId())).first();
		});

		assertThat(stored).containsAllEntriesOf(new Document("_id", rogelio.getId())
				.append("username", rogelio.getUserName().getUsername())
				.append("primary_email", rogelio.getEmail().email())
		);
	}


	/**
	 * Query using the value type.
	 */
	@Test
	void queryViaValueType() {
		assertThat(repository.findByUserName(jane.getUserName())).isEqualTo(jane);
	}

	/**
	 * Query using a property of the embedded value type
	 */
	@Test
	void queryViaPropertyOfValueType() {
		assertThat(repository.findByEmailEmail(jane.getEmail().email())).isEqualTo(jane);
	}
}
