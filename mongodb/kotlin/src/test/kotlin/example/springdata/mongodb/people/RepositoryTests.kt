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
package example.springdata.mongodb.people

import example.springdata.mongodb.util.MongoContainers
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * Tests showing Kotlin usage of Spring Data Repositories.
 *
 * @author Mark Paluch
 */
@Testcontainers
@DataMongoTest
class RepositoryTests {

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
	lateinit var repository: PersonRepository

	@BeforeEach
	fun before() {
		repository.deleteAll()
	}

	@Test
	fun `should find one person`() {

		repository.save(Person("Walter", "White"))

		val walter = repository.findOneByFirstname("Walter")

		assertThat(walter).isNotNull()
		assertThat(walter.firstname).isEqualTo("Walter")
		assertThat(walter.lastname).isEqualTo("White")
	}

	@Test
	fun `should return null if no person found`() {

		repository.save(Person("Walter", "White"))

		val walter = repository.findOneOrNoneByFirstname("Hank")

		assertThat(walter).isNull()
	}

	@Test
	fun `should throw EmptyResultDataAccessException if no person found`() {

		repository.save(Person("Walter", "White"))

		assertThatThrownBy { repository.findOneByFirstname("Hank") }.isInstanceOf(EmptyResultDataAccessException::class.java)
	}

	@Test
	fun `should accept nullable parameters`() {

		repository.save(Person(null, "Heisenberg"))

		val heisenberg = repository.findNullableByFirstname(null)

		assertThat(heisenberg).isNotNull()
		assertThat(heisenberg!!.lastname).isEqualTo("Heisenberg")
	}
}
