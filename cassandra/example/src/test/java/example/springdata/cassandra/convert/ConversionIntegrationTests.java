/*
 * Copyright 2016-2018 the original author or authors.
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

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.TupleValue;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConverterConfiguration.class)
public class ConversionIntegrationTests {

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost();

	@Autowired CassandraOperations operations;

	@Before
	public void setUp() {
		operations.truncate(Addressbook.class);
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

		assertThat(row).isNotNull();

		assertThat(row.getString("id")).isEqualTo("private");
		assertThat(row.getString("me")).contains("\"firstname\":\"Walter\"");
		assertThat(row.getList("friends", String.class)).hasSize(2);
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

		assertThat(loaded.getMe()).isEqualTo(addressbook.getMe());
		assertThat(loaded.getFriends()).isEqualTo(addressbook.getFriends());
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

		assertThat(loaded.getTheId()).isEqualTo(addressbook.getId());
		assertThat(loaded.getMyDetailsAsJson()).contains("\"firstname\":\"Walter\"");
	}

	/**
	 * Creates and stores a new {@link Addressbook} inside of Cassandra writing map and tuple columns.
	 */
	@Test
	public void shouldWriteConvertedMapsAndTuples() {

		Addressbook addressbook = new Addressbook();
		addressbook.setId("private");

		Map<Integer, Currency> preferredCurrencies = new HashMap<>();
		preferredCurrencies.put(1, Currency.getInstance("USD"));
		preferredCurrencies.put(2, Currency.getInstance("EUR"));

		Address address = new Address();
		address.setAddress("3828 Piermont Dr");
		address.setCity("Albuquerque");
		address.setZip("87111");

		addressbook.setPreferredCurrencies(preferredCurrencies);
		addressbook.setAddress(address);

		operations.insert(addressbook);

		Row row = operations.selectOne(QueryBuilder.select().from("addressbook"), Row.class);

		assertThat(row).isNotNull();

		TupleValue tupleValue = row.getTupleValue("address");
		assertThat(tupleValue.getString(0)).isEqualTo(address.getAddress());
		assertThat(tupleValue.getString(1)).isEqualTo(address.getCity());
		assertThat(tupleValue.getString(2)).isEqualTo(address.getZip());

		Map<Integer, String> rawPreferredCurrencies = row.getMap("preferredCurrencies", Integer.class, String.class);

		assertThat(rawPreferredCurrencies).containsEntry(1, "USD").containsEntry(2, "EUR");
	}
}
