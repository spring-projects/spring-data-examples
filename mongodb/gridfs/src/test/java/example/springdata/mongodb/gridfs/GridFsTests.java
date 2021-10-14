/*
 * Copyright 2018-2021 the original author or authors.
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
package example.springdata.mongodb.gridfs;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.*;

import example.springdata.mongodb.util.MongoContainers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.StreamUtils;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Tests to show the usage of {@link GridFsOperations} with Spring Data MongoDB.
 *
 * @author Hartmut Lang
 * @author Mark Paluch
 */
@Testcontainers
@DataMongoTest
class GridFsTests {

	@Container //
	private static MongoDBContainer mongoDBContainer = MongoContainers.getDefaultContainer();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired GridFsOperations gridFsOperations;

	@BeforeEach
	void before() {
		gridFsOperations.delete(new Query());
	}

	@Test
	void shouldStoreSimpleFile() throws IOException {

		try (InputStream is = new BufferedInputStream(new ClassPathResource("./example-file.txt").getInputStream())) {
			// store file
			gridFsOperations.store(is, "example-file.txt");

		}

		// get file or resource by filename
		var file = gridFsOperations.findOne(query(whereFilename().is("example-file.txt")));

		assertThat(file.getFilename()).isEqualTo("example-file.txt");
	}

	@Test
	void shouldStoreFileWithMetadata() throws IOException {

		try (InputStream is = new BufferedInputStream(new ClassPathResource("./example-file.txt").getInputStream())) {

			// store file with metaData
			var customerMetaData = new Customer("Hardy", "Lang");
			gridFsOperations.store(is, "example-file.txt", customerMetaData);
		}

		// search by metaData
		var file = gridFsOperations.findOne(query(whereMetaData("firstName").is("Hardy")));
		assertThat(file.getFilename()).isEqualTo("example-file.txt");
	}

	@Test
	void shouldStoreAndReadFile() throws IOException {

		byte[] bytes;
		try (InputStream is = new BufferedInputStream(new ClassPathResource("./example-file.txt").getInputStream())) {
			bytes = StreamUtils.copyToByteArray(is);
		}

		// store file
		gridFsOperations.store(new ByteArrayInputStream(bytes), "example-file.txt");

		var resource = gridFsOperations.getResource("example-file.txt");

		byte[] loaded;
		try (var is = resource.getInputStream()) {
			loaded = StreamUtils.copyToByteArray(is);
		}

		assertThat(bytes).isEqualTo(loaded);
	}
}
