/*
 * Copyright 2020 the original author or authors.
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
package example.springdata.mongodb.aggregationupdate;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.client.MongoCollection;

/**
 * @author Christoph Strobl
 * @since 2020/08
 */
@ExtendWith(SpringExtension.class)
@Testcontainers
class ScoreRepositoryTests {

	@Container private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(
			DockerImageName.parse("mongo:4.2.8"));

	@Autowired MongoOperations operations;
	@Autowired ScoreRepository repository;

	@Configuration
	@EnableMongoRepositories
	static class Config extends AbstractMongoClientConfiguration {

		@Override
		protected String getDatabaseName() {
			return "test";
		}

		@Override
		protected void configureClientSettings(Builder builder) {
			builder.applyConnectionString(new ConnectionString(MONGO_DB_CONTAINER.getReplicaSetUrl()));
		}
	}

	@SuppressWarnings("unchecked")
	@BeforeEach
	void beforeEach() throws Exception {

		repository.deleteAll();

		Score score1 = new Score(1, "Maya", Arrays.asList(10, 5, 10), Arrays.asList(10, 8), 0);
		Score score2 = new Score(2, "Ryan", Arrays.asList(5, 6, 5), Arrays.asList(8, 8), 8);

		repository.saveAll(Arrays.asList(score1, score2));
	}

	@Test
	void testAggregationUpdate() {

		repository.calculateTotalScore();

		assertThat(nativeConnection(Score.class).find(new org.bson.Document()).into(new ArrayList<>()))
				.containsExactlyInAnyOrder( //
						org.bson.Document.parse(
								"{\"_id\" : 1, \"student\" : \"Maya\", \"homework\" : [ 10, 5, 10 ], \"quiz\" : [ 10, 8 ], \"extraCredit\" : 0, \"totalHomework\" : 25, \"totalQuiz\" : 18, \"totalScore\" : 43,  \"_class\" : \"example.springdata.mongodb.aggregationupdate.Score\"}"),
						org.bson.Document.parse(
								"{ \"_id\" : 2, \"student\" : \"Ryan\", \"homework\" : [ 5, 6, 5 ], \"quiz\" : [ 8, 8 ], \"extraCredit\" : 8, \"totalHomework\" : 16, \"totalQuiz\" : 16, \"totalScore\" : 40, \"_class\" : \"example.springdata.mongodb.aggregationupdate.Score\"}"));
	}

	private MongoCollection<Document> nativeConnection(Class<?> type) {
		return operations.getCollection(operations.getCollectionName(type));
	}
}
