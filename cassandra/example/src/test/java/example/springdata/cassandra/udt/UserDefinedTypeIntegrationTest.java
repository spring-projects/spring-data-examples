/*
 * Copyright 2017 the original author or authors.
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
package example.springdata.cassandra.udt;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.RequiresCassandraKeyspace;

import java.util.Collections;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;

/**
 * Integration test to show User-Defined type support.
 * 
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDefinedTypeIntegrationTest {

	@ClassRule public final static RequiresCassandraKeyspace CASSANDRA_KEYSPACE = RequiresCassandraKeyspace.onLocalhost();

	@Configuration
	static class Config extends AbstractCassandraConfiguration {

		@Override
		public String getKeyspaceName() {
			return "example";
		}

		@Override
		public String[] getEntityBasePackages() {
			return new String[] { Person.class.getPackage().getName() };
		}

		@Override
		public SchemaAction getSchemaAction() {
			return SchemaAction.RECREATE;
		}
	}

	@Autowired CassandraOperations operations;
	@Autowired CassandraAdminOperations adminOperations;

	@Before
	public void before() throws Exception {
		operations.truncate("person");
	}

	/**
	 * Insert a row with a mapped User-defined type.
	 */
	@Test
	public void insertMappedUdt() {

		Person person = new Person();
		person.setId(42);
		person.setFirstname("Walter");
		person.setLastname("White");

		person.setCurrent(new Address("308 Negra Arroyo Lane", "87104", "Albuquerque"));
		person.setPrevious(Collections.singletonList(new Address("12000 – 12100 Coors Rd SW", "87045", "Albuquerque")));

		operations.insert(person);

		Person loaded = operations.selectOne("SELECT * FROM person WHERE id = 42", Person.class);

		assertThat(loaded.getCurrent()).isEqualTo(person.getCurrent());
		assertThat(loaded.getPrevious()).containsAll(person.getPrevious());
	}

	/**
	 * Insert a row with a raw User-defined type.
	 */
	@Test
	public void insertRawUdt() {

		KeyspaceMetadata keyspaceMetadata = adminOperations.getKeyspaceMetadata();
		UserType address = keyspaceMetadata.getUserType("address");

		UDTValue udtValue = address.newValue();
		udtValue.setString("street", "308 Negra Arroyo Lane");
		udtValue.setString("zip", "87104");
		udtValue.setString("city", "Albuquerque");

		Person person = new Person();
		person.setId(42);
		person.setFirstname("Walter");
		person.setLastname("White");

		person.setAlternative(udtValue);

		operations.insert(person);

		Person loaded = operations.selectOne("SELECT * FROM person WHERE id = 42", Person.class);

		assertThat(loaded.getAlternative().getString("zip")).isEqualTo("87104");
	}
}
