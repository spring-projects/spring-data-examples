/*
 * Copyright 2018 the original author or authors.
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
package example.springdata.cassandra.kotlin

import example.springdata.cassandra.util.CassandraKeyspace
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.util.Version
import org.springframework.test.context.junit4.SpringRunner

/**
 * Tests showing Kotlin usage of Spring Data Repositories.
 *
 * @author Mark Paluch
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class RepositoryTests {

	companion object {

		@JvmField
		@ClassRule
		val CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost()
				.atLeast(Version.parse("3.0"))
	}

	@Autowired
	lateinit var repository: PersonRepository

	@Before
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
}
