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
package example.springdata.cassandra.kotlin

import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import example.springdata.cassandra.util.CassandraKeyspace
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.cassandra.core.*
import org.springframework.data.cassandra.core.query.Query.query
import org.springframework.data.cassandra.core.query.isEqualTo
import org.springframework.data.cassandra.core.query.where

/**
 * Tests showing Kotlin usage of [MongoTemplate] and its Kotlin extensions.
 *
 * @author Mark Paluch
 */
@CassandraKeyspace
@SpringBootTest
class TemplateTests {

	@Autowired
	lateinit var operations: CassandraOperations

	@BeforeEach
	fun before() {
		operations.truncate<Person>()
	}

	@Test
	fun `should create collection leveraging reified type parameters`() {
		assertThat(operations.getTableName<Person>()).isEqualTo(CqlIdentifier.fromCql("person"))
	}

	@Test
	fun `should insert and find person in a fluent API style`() {

		operations.insert<Person>().inTable("person").one(Person("Walter", "White"))

		val people = operations.query<Person>()
				.matching(query(where("firstname").isEqualTo("Walter")))
				.all()

		assertThat(people).hasSize(1).extracting("firstname").containsOnly("Walter")
	}

	@Test
	fun `should insert and project query results`() {

		operations.insert<Person>().inTable("person").one(Person("Walter", "White"))

		val firstnameOnly = operations.query<Person>()
				.asType<FirstnameOnly>()
				.matching(query(where("firstname").isEqualTo("Walter")))
				.oneValue()

		assertThat(firstnameOnly?.getFirstname()).isEqualTo("Walter")
	}

	@Test
	fun `should insert and count objects in a fluent API style`() {

		operations.insert<Person>().inTable("person").one(Person("Walter", "White"))

		val count = operations.query<Person>()
				.matching(query(where("firstname").isEqualTo("Walter")))
				.count()

		assertThat(count).isEqualTo(1)
	}

	@Test
	fun `should insert and find person`() {

		val person = Person("Walter", "White")

		operations.insert(person)

		val people = operations.select<Person>(query(where("firstname").isEqualTo("Walter")))

		assertThat(people).hasSize(1).extracting("firstname").containsOnly("Walter")
	}

	@Test
	fun `should apply defaulting for absent properties`() {

		operations.cqlOperations.execute(QueryBuilder.insertInto("person").value("firstname", QueryBuilder.literal("Walter")).asCql())

		val person = operations.query<Person>()
				.matching(query(where("firstname").isEqualTo("Walter")))
				.firstValue()!!

		assertThat(person.firstname).isEqualTo("Walter")
		assertThat(person.lastname).isEqualTo("White")


		val resultSet = operations.cqlOperations.queryForResultSet("SELECT * FROM person WHERE firstname = 'Walter'")
		val walter = resultSet.one()!!

		assertThat(walter).isNotNull
		assertThat(walter.getString("firstname")).isEqualTo("Walter")
		assertThat(walter.getString("lastname")).isNull()
	}

	interface FirstnameOnly {
		fun getFirstname(): String
	}
}
