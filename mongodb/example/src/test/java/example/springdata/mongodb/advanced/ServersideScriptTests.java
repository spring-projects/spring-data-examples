/*
 * Copyright 2015 the original author or authors.
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
package example.springdata.mongodb.advanced;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;

import example.springdata.mongodb.customer.Customer;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.data.mongodb.core.script.NamedMongoScript;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Christoph Strobl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
public class ServersideScriptTests {

	@Autowired AdvancedRepository repository;
	@Autowired MongoOperations operations;

	@Before
	public void setUp() {

		if (!operations.collectionExists(Customer.class)) {
			operations.createCollection(Customer.class);
		}

		// just make sure we remove everything properly
		operations.getCollection("system.js").remove(new BasicDBObject());
		repository.deleteAll();
	}

	/**
	 * Store and call an arbitrary JavaScript function (in this case a simple echo script) via its name.
	 */
	@Test
	public void saveAndCallScriptViaName() {

		operations.scriptOps()
				.register(new NamedMongoScript("echoScript", new ExecutableMongoScript("function(x) { return x; }")));

		Object o = operations.scriptOps().call("echoScript", "Hello echo...!");
		assertThat(o, is((Object) "Hello echo...!"));
	}

	/**
	 * Use a script execution to create an atomic put-if-absent operation that fulfills the contract of
	 * {@link Map#putIfAbsent(Object, Object)}
	 */
	@Test
	public void complexScriptExecutionSimulatingPutIfAbsent() {

		Customer ned = new Customer("Ned", "Stark");
		ned.setId("ned-stark");

		// #1: on first insert null has to be returned
		assertThat(operations.scriptOps().execute(createExecutablePutIfAbsentScript(ned)), nullValue());

		// #2: change the firstname and put the object again, we expect a return value.
		ned.setFirstname("Eddard");
		assertThat(operations.scriptOps().execute(createExecutablePutIfAbsentScript(ned)), notNullValue());

		// #3: make sure the entity has not been altered by #2
		assertThat(repository.findOne(ned.getId()).getFirstname(), is("Ned"));
		assertThat(repository.count(), is(1L));
	}

	private ExecutableMongoScript createExecutablePutIfAbsentScript(Customer customer) {

		String collectionName = operations.getCollectionName(Customer.class);
		Object id = operations.getConverter().getMappingContext().getPersistentEntity(Customer.class)
				.getIdentifierAccessor(customer).getIdentifier();

		DBObject dbo = new BasicDBObject();
		operations.getConverter().write(customer, dbo);

		String scriptString = String.format(
				"object  =  db.%1$s.findOne('{\"_id\": \"%2$s\"}'); if (object == null) { db.%1s.insert(%3$s); return null; } else { return object; }",
				collectionName, id, dbo);

		return new ExecutableMongoScript(scriptString);
	}
}
