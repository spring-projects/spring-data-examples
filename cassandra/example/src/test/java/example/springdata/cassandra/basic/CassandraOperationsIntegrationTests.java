/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.cassandra.basic;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.AsyncCassandraTemplate;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;

/**
 * Integration test showing the basic usage of {@link CassandraTemplate}.
 *
 * @author Mark Paluch
 */
@SpringBootTest(classes = BasicConfiguration.class)
@CassandraKeyspace
class CassandraOperationsIntegrationTests {


	@Autowired CqlSession session;
	@Autowired CassandraOperations template;

	@BeforeEach
	void setUp() throws Exception {
		template.getCqlOperations().execute("TRUNCATE users");
	}

	/**
	 * Cassandra {@link com.datastax.driver.core.Statement}s can be used together with {@link CassandraTemplate} and the
	 * mapping layer.
	 */
	@Test
	void insertAndSelect() {

		var insert = QueryBuilder.insertInto("users").value("user_id", QueryBuilder.literal(42L)) //
				.value("uname", QueryBuilder.literal("heisenberg")) //
				.value("fname", QueryBuilder.literal("Walter")) //
				.value("lname", QueryBuilder.literal("White")) //
				.ifNotExists(); //

		template.getCqlOperations().execute(insert.asCql());

		var user = template.selectOneById(42L, User.class);
		assertThat(user.getUsername()).isEqualTo("heisenberg");

		var users = template.select(QueryBuilder.selectFrom("users").all().asCql(), User.class);
		assertThat(users).hasSize(1);
		assertThat(users.get(0)).isEqualTo(user);
	}

	/**
	 * Objects can be inserted and updated using {@link CassandraTemplate}. What you {@code update} is what you
	 * {@code select}.
	 */
	@Test
	void insertAndUpdate() {

		var user = new User();
		user.setId(42L);
		user.setUsername("heisenberg");
		user.setFirstname("Walter");
		user.setLastname("White");

		template.insert(user);

		user.setFirstname(null);
		template.update(user);

		var loaded = template.selectOneById(42L, User.class);
		assertThat(loaded.getUsername()).isEqualTo("heisenberg");
		assertThat(loaded.getFirstname()).isNull();
	}

	/**
	 * Asynchronous query execution using callbacks.
	 */
	@Test
	void insertAsynchronously() throws InterruptedException {

		var user = new User();
		user.setId(42L);
		user.setUsername("heisenberg");
		user.setFirstname("Walter");
		user.setLastname("White");

		final var countDownLatch = new CountDownLatch(1);

		var asyncTemplate = new AsyncCassandraTemplate(session);

		var future = asyncTemplate.insert(user);

		future.addCallback(it -> countDownLatch.countDown(), throwable -> countDownLatch.countDown());

		countDownLatch.await(5, TimeUnit.SECONDS);

		var loaded = template.selectOneById(user.getId(), User.class);
		assertThat(loaded).isEqualTo(user);
	}

	/**
	 * {@link CassandraTemplate} allows selection of projections on template-level. All basic data types including
	 * {@link Row} can be selected.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void selectProjections() {

		var user = new User();
		user.setId(42L);
		user.setUsername("heisenberg");
		user.setFirstname("Walter");
		user.setLastname("White");

		template.insert(user);

		var id = template.selectOne(QueryBuilder.selectFrom("users").column("user_id").asCql(), Long.class);
		assertThat(id).isEqualTo(user.getId());

		var row = template.selectOne(QueryBuilder.selectFrom("users").column("user_id").asCql(), Row.class);
		assertThat(row.getLong(0)).isEqualTo(user.getId());

		Map<String, Object> map = template.selectOne(QueryBuilder.selectFrom("users").all().asCql(), Map.class);
		assertThat(map).containsEntry("user_id", user.getId());
		assertThat(map).containsEntry("fname", "Walter");
	}
}
