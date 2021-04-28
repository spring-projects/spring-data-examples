/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.cassandra.udt;

import static org.assertj.core.api.Assertions.*;

import example.springdata.cassandra.util.CassandraKeyspace;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.metadata.schema.KeyspaceMetadata;
import com.datastax.oss.driver.api.core.type.UserDefinedType;

/**
 * Integration test to show User-Defined type support.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 */
@SpringBootTest
@CassandraKeyspace
class UserDefinedTypeIntegrationTest {

	@Configuration
	static class Config extends AbstractCassandraConfiguration {

		@Override
		protected int getPort() {
			return Integer.getInteger("spring.data.cassandra.port");
		}

		@Override
		protected String getContactPoints() {
			return System.getProperty("spring.data.cassandra.contact-points");
		}

		@Override
		public String getKeyspaceName() {
			return "example";
		}

		@Override
		protected String getLocalDataCenter() {
			return "datacenter1";
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

	@BeforeEach
	void before() throws Exception {
		operations.getCqlOperations().execute("TRUNCATE person");
	}

	/**
	 * Insert a row with a mapped User-defined type.
	 */
	@Test
	void insertMappedUdt() {

		var person = new Person();
		person.setId(42);
		person.setFirstname("Walter");
		person.setLastname("White");

		person.setCurrent(new Address("308 Negra Arroyo Lane", "87104", "Albuquerque"));
		person.setPrevious(Collections.singletonList(new Address("12000 – 12100 Coors Rd SW", "87045", "Albuquerque")));

		operations.insert(person);

		var loaded = operations.selectOne("SELECT * FROM person WHERE id = 42", Person.class);

		assertThat(loaded.getCurrent()).isEqualTo(person.getCurrent());
		assertThat(loaded.getPrevious()).containsAll(person.getPrevious());
	}

	/**
	 * Insert a row with a raw User-defined type.
	 */
	@Test
	void insertRawUdt() {

		var keyspaceMetadata = adminOperations.getKeyspaceMetadata();
		var address = keyspaceMetadata.getUserDefinedType("address").get();

		var udtValue = address.newValue();
		udtValue.setString("street", "308 Negra Arroyo Lane");
		udtValue.setString("zip", "87104");
		udtValue.setString("city", "Albuquerque");

		var person = new Person();
		person.setId(42);
		person.setFirstname("Walter");
		person.setLastname("White");

		person.setAlternative(udtValue);

		operations.insert(person);

		var loaded = operations.selectOne("SELECT * FROM person WHERE id = 42", Person.class);

		assertThat(loaded.getAlternative().getString("zip")).isEqualTo("87104");
	}
}
