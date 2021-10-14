/*
 * Copyright 2018-2021 the original author or authors.
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
package example.springdata.mongodb.schema;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.schema.JsonSchemaProperty.*;

import example.springdata.mongodb.util.MongoContainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Christoph Strobl
 */
@Testcontainers
@DataMongoTest
class DocumentValidationTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	private static final String COLLECTION = "star-wars";

	@Autowired MongoOperations mongoOps;

	@BeforeEach
	void setUp() {
		mongoOps.dropCollection(COLLECTION);
	}

	/**
	 * MongoDB (as of version 3.2) supports validating documents against a given structure described by a query. The
	 * structure can be built from {@link org.springframework.data.mongodb.core.query.Criteria} objects in the same way as
	 * they are used for defining queries.
	 *
	 * <pre>
	 *     <code>
	 * {
	 *     name : {
	 *         $exists : true,
	 *         $ne : null,
	 *         $type : 2
	 *     },
	 *     age : {
	 *         $exists : true,
	 *         $ne : null,
	 *         $type : 16,
	 *         $gte : 0,
	 *         $lte : 125
	 *     }
	 * }
	 *     </code>
	 * </pre>
	 */
	@Test
	void criteriaValidator() {

		var validator = Validator.criteria( //
				where("name").exists(true).ne(null).type(2) // non null String
						.and("age").exists(true).ne(null).type(16).gte(0).lte(125)) // non null int between 0 and 125
		;

		mongoOps.createCollection(Jedi.class, CollectionOptions.empty().validator(validator));

		assertThat(mongoOps.save(new Jedi("luke", "luke", "skywalker", 25))).isNotNull();

		assertThatExceptionOfType(DataIntegrityViolationException.class)
				.isThrownBy(() -> mongoOps.save(new Jedi("yoda", "yoda", null, 900)));
	}

	/**
	 * As of version 3.6, MongoDB supports collections that validate documents against a provided JSON Schema that
	 * complies to the JSON schema specification (draft 4).
	 *
	 * <pre>
	 *     <code>
	 * {
	 *   "type": "object",
	 *   "required": [ "name", "age" ],
	 *   "properties": {
	 *     "name": {
	 *       "type": "string",
	 *       "minLength": 1
	 *     },
	 *     "age": {
	 *       "type": "int",
	 *       "minimum" : 0,
	 *       "exclusiveMinimum" : false,
	 *       "maximum" : 125,
	 *       "exclusiveMaximum" : false
	 *     }
	 *   }
	 * }
	 *     </code>
	 * </pre>
	 */
	@Test
	void schemaValidator() {

		var validator = Validator.schema(MongoJsonSchema.builder() //
				.required("name", "age") //
				.properties( //
						string("name").minLength(1), //
						int32("age").gte(0).lte(125) //
				).build());
		mongoOps.createCollection(Jedi.class, CollectionOptions.empty().validator(validator));

		assertThat(mongoOps.save(new Jedi("luke", "luke", "skywalker", 25))).isNotNull();

		assertThatExceptionOfType(DataIntegrityViolationException.class)
				.isThrownBy(() -> mongoOps.save(new Jedi("yoda", "yoda", null, 900)));
	}
}
