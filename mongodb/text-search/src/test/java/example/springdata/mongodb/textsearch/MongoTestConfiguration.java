/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.mongodb.textsearch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

import example.springdata.mongodb.util.BlogPostInitializer;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Configuration
@EnableAutoConfiguration
public class MongoTestConfiguration {

	@Autowired MongoOperations operations;

	/**
	 * Initializes the repository with a predefined set of entities.
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostConstruct
	void initialize() throws Exception {
		BlogPostInitializer.INSTANCE.initialize(operations);
	}

	/**
	 * Clean up after execution by dropping used test db instance.
	 * 
	 * @throws Exception
	 */
	@PreDestroy
	void dropTestDB() throws Exception {
		operations.dropCollection(BlogPost.class);
	}
}
