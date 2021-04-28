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
package example.springdata.cassandra.convert;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.TupleValue;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;

/**
 * @author Mark Paluch
 */
@CassandraKeyspace
@SpringBootTest(classes = ConverterConfiguration.class)
class ConversionIntegrationTests {

	@Autowired CassandraOperations operations;

	@BeforeEach
	void setUp() {
		operations.truncate(Addressbook.class);
	}

	/**
	 * Creates and stores a new {@link Addressbook} inside of Cassandra. {@link Contact} classes are converted using the
	 * custom {@link example.springdata.cassandra.convert.ConverterConfiguration.PersonWriteConverter}.
	 */
	@Test
	void shouldCreateAddressbook() {

		var addressbook = new Addressbook();
		addressbook.setId("private");

		addressbook.setMe(new Contact("Walter", "White"));
		addressbook.setFriends(Arrays.asList(new Contact("Jesse", "Pinkman"), new Contact("Saul", "Goodman")));

		operations.insert(addressbook);

		var row = operations.selectOne(QueryBuilder.selectFrom("addressbook").all().asCql(), Row.class);

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
	void shouldReadAddressbook() {

		var addressbook = new Addressbook();
		addressbook.setId("private");

		addressbook.setMe(new Contact("Walter", "White"));
		addressbook.setFriends(Arrays.asList(new Contact("Jesse", "Pinkman"), new Contact("Saul", "Goodman")));

		operations.insert(addressbook);

		var loaded = operations.selectOne(QueryBuilder.selectFrom("addressbook").all().asCql(), Addressbook.class);

		assertThat(loaded.getMe()).isEqualTo(addressbook.getMe());
		assertThat(loaded.getFriends()).isEqualTo(addressbook.getFriends());
	}

	/**
	 * Creates and stores a new {@link Addressbook} inside of Cassandra. The {@link Addressbook} is read back to a
	 * {@link CustomAddressbook} class using the
	 * {@link example.springdata.cassandra.convert.ConverterConfiguration.CustomAddressbookReadConverter}.
	 */
	@Test
	void shouldReadCustomAddressbook() {

		var addressbook = new Addressbook();
		addressbook.setId("private");

		addressbook.setMe(new Contact("Walter", "White"));

		operations.insert(addressbook);

		var loaded = operations.selectOne(QueryBuilder.selectFrom("addressbook").all().asCql(),
				CustomAddressbook.class);

		assertThat(loaded.theId()).isEqualTo(addressbook.getId());
		assertThat(loaded.myDetailsAsJson()).contains("\"firstname\":\"Walter\"");
	}

	/**
	 * Creates and stores a new {@link Addressbook} inside of Cassandra writing map and tuple columns.
	 */
	@Test
	void shouldWriteConvertedMapsAndTuples() {

		var addressbook = new Addressbook();
		addressbook.setId("private");

		Map<Integer, Currency> preferredCurrencies = new HashMap<>();
		preferredCurrencies.put(1, Currency.getInstance("USD"));
		preferredCurrencies.put(2, Currency.getInstance("EUR"));

		var address = new Address("3828 Piermont Dr", "Albuquerque", "87111");

		addressbook.setPreferredCurrencies(preferredCurrencies);
		addressbook.setAddress(address);

		operations.insert(addressbook);

		var row = operations.selectOne(QueryBuilder.selectFrom("addressbook").all().asCql(), Row.class);

		assertThat(row).isNotNull();

		var tupleValue = row.getTupleValue("address");
		assertThat(tupleValue.getString(0)).isEqualTo(address.address());
		assertThat(tupleValue.getString(1)).isEqualTo(address.city());
		assertThat(tupleValue.getString(2)).isEqualTo(address.zip());

		var rawPreferredCurrencies = row.getMap("preferredCurrencies", Integer.class, String.class);

		assertThat(rawPreferredCurrencies).containsEntry(1, "USD").containsEntry(2, "EUR");
	}
}
