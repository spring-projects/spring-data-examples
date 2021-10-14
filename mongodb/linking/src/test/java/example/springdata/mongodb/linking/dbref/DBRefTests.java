/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.mongodb.linking.dbref;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import example.springdata.mongodb.util.MongoContainers;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.DBRef;

/**
 * @author Christoph Strobl
 */
@Testcontainers
@DataMongoTest
public class DBRefTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired MongoOperations operations;

	@Test
	void saveAndLoadDbRef() {

		Manager manager = new Manager("jabba-the-hutt", "jabba");

		operations.save(manager);

		Employee employee1 = new Employee("greedo-tetsu-jr", "greedo");
		employee1.setManager(manager);
		operations.save(employee1);
		operations.update(Manager.class) //
				.matching(where("id").is(manager.getId())) //
				.apply(new Update().push("employees").value(employee1)) //
				.first();

		Employee employee2 = new Employee("boba-fett", "boba");
		employee2.setManager(manager);
		operations.save(employee2);
		operations.update(Manager.class) //
				.matching(where("id").is(manager.getId())) //
				.apply(new Update().push("employees").value(employee2)) //
				.first();

		operations.execute(Manager.class, collection -> {

			Document rawManager = collection.find(new Document("_id", manager.getId())).first();
			assertThat(rawManager.get("employees", List.class)) //
					.containsExactly( //
							new DBRef("employee", "greedo-tetsu-jr"), //
							new DBRef("employee", "boba-fett") //
					);
			return "OK";
		});

		Manager loaded = operations.query(Manager.class)
				.matching(where("id").is(manager.getId()))
				.firstValue();

		assertThat(loaded.getEmployees()) //
				.allMatch(it -> it instanceof Employee) //
				.extracting("name").containsExactly("greedo", "boba");
	}
}
