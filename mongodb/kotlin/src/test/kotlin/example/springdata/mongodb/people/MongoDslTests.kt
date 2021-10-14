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
package example.springdata.mongodb.people;

import example.springdata.mongodb.util.MongoContainers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.insert
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.regex
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * Tests showing the Mongo Criteria DSL.
 *
 * @author Christoph Strobl
 */
@Testcontainers
@DataMongoTest
class MongoDslTests {

	companion object {
		@Container //
		private val mongoDBContainer = MongoContainers.getDefaultContainer()

		@JvmStatic
		@DynamicPropertySource
		fun setProperties(registry: DynamicPropertyRegistry) {
			registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
		}
	}

	@Autowired
	lateinit var operations: MongoOperations

	@BeforeEach
	fun before() {
		operations.dropCollection<Person>()
	}

	@Test
	fun `simple type-safe find`() {

		operations.insert<Person>().one(Person("Tyrion", "Lannister"))
		operations.insert<Person>().one(Person("Cersei", "Lannister"))

		val people = operations.find<Person>(Query(Person::firstname isEqualTo "Tyrion"))
		assertThat(people).hasSize(1).extracting("firstname").containsOnly("Tyrion")
	}

	@Test
	fun `more complex type-safe find`() {

		operations.insert<Person>().one(Person("Tyrion", "Lannister"))
		operations.insert<Person>().one(Person("Cersei", "Lannister"))

		val people = operations.find<Person>(Query( //
				Criteria().andOperator(Person::lastname isEqualTo "Lannister", Person::firstname regex "^Cer.*") //
		))
		assertThat(people).hasSize(1).extracting("firstname").containsOnly("Cersei")
	}
}
