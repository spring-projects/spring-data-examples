/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.mongodb.immutable;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

/**
 * Test configuration to connect to a MongoDB named "test" using a {@code MongoClient}. <br />
 * Also enables Spring Data repositories for MongoDB.
 *
 * @author Mark Paluch
 * @author Christoph Strobl
 */
@SpringBootApplication
class ApplicationConfiguration {

	/**
	 * Register the {@link BeforeConvertCallback} used to update an {@link ImmutablePerson} before handing over the newly
	 * created instance to the actual mapping layer performing the conversion into the store native
	 * {@link org.bson.Document} representation.
	 *
	 * @return a {@link BeforeConvertCallback} for {@link ImmutablePerson}.
	 */
	@Bean
	BeforeConvertCallback<ImmutablePerson> beforeConvertCallback() {

		return (immutablePerson, collection) -> {

			var randomNumber = ThreadLocalRandom.current().nextInt(1, 100);

			// withRandomNumber is a so called wither method returning a new instance of the entity with a new value assigned
			return immutablePerson.withRandomNumber(randomNumber);
		};
	}

	/**
	 * Register the {@link BeforeConvertCallback} used to update an {@link ImmutableRecord} before handing over the newly
	 * created instance to the actual mapping layer performing the conversion into the store native
	 * {@link org.bson.Document} representation.
	 *
	 * @return a {@link BeforeConvertCallback} for {@link ImmutablePerson}.
	 */
	@Bean
	BeforeConvertCallback<ImmutableRecord> beforeRecordConvertCallback() {

		return (rec, collection) -> {

			var randomNumber = ThreadLocalRandom.current().nextInt(1, 100);

			return new ImmutableRecord(rec.id(), randomNumber);
		};
	}

}
