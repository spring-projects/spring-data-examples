/*
 * Copyright 2015-2018 the original author or authors.
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
package example.springdata.mongodb.advanced;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.customer.Customer;

import java.util.Map;

import org.bson.Document;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.data.mongodb.core.script.NamedMongoScript;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServersideScriptTests {

	@Autowired AdvancedRepository repository;
	@Autowired MongoOperations operations;

	@Before
	public void setUp() {

		if (!operations.collectionExists(Customer.class)) {
			operations.createCollection(Customer.class);
		}

		// just make sure we remove everything properly
		operations.getCollection("system.js").deleteMany(new Document());
		repository.deleteAll();
	}

	/**
	 * Store and call an arbitrary JavaScript function (in this case a simple echo script) via its name.
	 */
	@Test
	public void saveAndCallScriptViaName() {

		operations.scriptOps()
				.register(new NamedMongoScript("echoScript", new ExecutableMongoScript("function(x) { return x; }")));

		assertThat(operations.scriptOps().call("echoScript", "Hello echo...!")).isEqualTo("Hello echo...!");
	}

	/**
	 * Use a script execution to create an atomic put-if-absent operation that fulfills the contract of
	 * {@link Map#putIfAbsent(Object, Object)}
	 */
	@Test
	@Ignore
	public void complexScriptExecutionSimulatingPutIfAbsent() {

		Customer ned = new Customer("Ned", "Stark");
		ned.setId("ned-stark");

		// #1: on first insert null has to be returned
		assertThat(operations.scriptOps().execute(createExecutablePutIfAbsentScript(ned))).isNotNull();

		// #2: change the firstname and put the object again, we expect a return value.
		ned.setFirstname("Eddard");
		assertThat(operations.scriptOps().execute(createExecutablePutIfAbsentScript(ned))).isNotNull();

		// #3: make sure the entity has not been altered by #2
		assertThat(repository.findById(ned.getId()))
				.hasValueSatisfying(it -> assertThat(it.getFirstname()).isEqualTo("Ned"));
		assertThat(repository.count()).isEqualTo(1L);
	}

	private ExecutableMongoScript createExecutablePutIfAbsentScript(Customer customer) {

		String collectionName = operations.getCollectionName(Customer.class);
		Object id = operations.getConverter().getMappingContext().getRequiredPersistentEntity(Customer.class)
				.getIdentifierAccessor(customer).getIdentifier();

		Document document = new Document();
		operations.getConverter().write(customer, document);

		String scriptString = String.format(
				"object  =  db.%1$s.findOne({\"_id\": \"%2$s\"}); if (object == null) { db.%1s.insert(%3$s); return null; } else { return object; }",
				collectionName, id, document);

		return new ExecutableMongoScript(scriptString);
	}
}
