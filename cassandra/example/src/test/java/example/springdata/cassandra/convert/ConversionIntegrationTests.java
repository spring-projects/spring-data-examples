/*
 * Copyright 2016 the original author or authors.
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
package example.springdata.cassandra.convert;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import example.springdata.cassandra.util.RequiresCassandraKeyspace;

/**
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConverterConfiguration.class)
public class ConversionIntegrationTests {

	@ClassRule public final static RequiresCassandraKeyspace CASSANDRA_KEYSPACE = RequiresCassandraKeyspace.onLocalhost();

	@Autowired CassandraOperations operations;

	@Before
	public void setUp() throws Exception {
		operations.truncate("addressbook");
	}

	/**
	 * Creates and stores a new {@link Addressbook} inside of Cassandra. {@link Contact} classes are converted using the
	 * custom {@link example.springdata.cassandra.convert.ConverterConfiguration.PersonWriteConverter}.
	 */
	@Test
	public void shouldCreateAddressbook() {

		Addressbook addressbook = new Addressbook();
		addressbook.setId("private");

		addressbook.setMe(new Contact("Walter", "White"));
		addressbook.setFriends(Arrays.asList(new Contact("Jesse", "Pinkman"), new Contact("Saul", "Goodman")));

		operations.insert(addressbook);

		Row row = operations.selectOne(QueryBuilder.select().from("addressbook"), Row.class);

		assertThat(row, is(notNullValue()));

		assertThat(row.getString("id"), is(equalTo("private")));
		assertThat(row.getString("me"), containsString("\"firstname\":\"Walter\""));
		assertThat(row.getList("friends", String.class), hasSize(2));
	}

	/**
	 * Creates and loads a new {@link Addressbook} inside of Cassandra. {@link Contact} classes are converted using the
	 * custom {@link example.springdata.cassandra.convert.ConverterConfiguration.PersonReadConverter}.
	 */
	@Test
	public void shouldReadAddressbook() {

		Addressbook addressbook = new Addressbook();
		addressbook.setId("private");

		addressbook.setMe(new Contact("Walter", "White"));
		addressbook.setFriends(Arrays.asList(new Contact("Jesse", "Pinkman"), new Contact("Saul", "Goodman")));

		operations.insert(addressbook);

		Addressbook loaded = operations.selectOne(QueryBuilder.select().from("addressbook"), Addressbook.class);

		assertThat(loaded.getMe(), is(equalTo(addressbook.getMe())));
		assertThat(loaded.getFriends(), is(equalTo(addressbook.getFriends())));
	}

	/**
	 * Creates and stores a new {@link Addressbook} inside of Cassandra. The {@link Addressbook} is read back to a
	 * {@link CustomAddressbook} class using the
	 * {@link example.springdata.cassandra.convert.ConverterConfiguration.CustomAddressbookReadConverter}.
	 */
	@Test
	public void shouldReadCustomAddressbook() {

		Addressbook addressbook = new Addressbook();
		addressbook.setId("private");

		addressbook.setMe(new Contact("Walter", "White"));

		operations.insert(addressbook);

		CustomAddressbook loaded = operations.selectOne(QueryBuilder.select().from("addressbook"), CustomAddressbook.class);

		assertThat(loaded.getTheId(), is(equalTo(addressbook.getId())));
		assertThat(loaded.getMyDetailsAsJson(), containsString("\"firstname\":\"Walter\""));
	}
}
