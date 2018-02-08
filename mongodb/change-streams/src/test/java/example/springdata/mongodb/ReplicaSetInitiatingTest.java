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
package example.springdata.mongodb;

import java.util.Arrays;

import org.bson.Document;
import org.hamcrest.number.IsCloseTo;
import org.junit.Assume;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 * Just a simple base Class initiating the required replica set.
 * 
 * @author Christoph Strobl
 */
abstract class ReplicaSetInitiatingTest {

	@Autowired MongoTemplate template;
	@Value("${spring.data.mongodb.port}") Integer port;

	/**
	 * Before we can actually continue we need to make sure that the Server works as a single node replica set which is
	 * required for Change Streams.
	 */
	@Before
	public void setUp() {

		MongoClient mongo = new MongoClient(new ServerAddress("localhost:" + port),
				MongoClientOptions.builder().connectTimeout(10).build());

		MongoDatabase admin = mongo.getDatabase("admin");
		Document status = admin.runCommand(new Document("serverStatus", "1"));

		if (!status.get("repl", Document.class).get("ismaster", Boolean.class)) {

			Document result = admin.runCommand(new Document("replSetInitiate", new Document("_id", "spring-data-examples")
					.append("members", Arrays.asList(new Document("_id", 0).append("host", "localhost:" + port)))));

			Assume.assumeThat(result.getDouble("ok"), IsCloseTo.closeTo(1.0D, 0D));
		}

		mongo.close();

		template.dropCollection(Person.class);
		template.createCollection(Person.class);
	}

}
