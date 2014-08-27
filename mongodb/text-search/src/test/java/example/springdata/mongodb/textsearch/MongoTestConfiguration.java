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

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import example.springdata.mongodb.util.BlogPostInitializer;

/**
 * @author Christoph Strobl
 */
@Configuration
@EnableMongoRepositories
public class MongoTestConfiguration extends AbstractMongoConfiguration {

	static final String DATABASE_NAME = "s2gx2014-blog";
	static final String BLOG_POST_ATOM_FEED_SOURCE = "https://spring.io/blog.atom";

	@Override
	protected String getDatabaseName() {
		return DATABASE_NAME;
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient();
	}

	/**
	 * Initializes the repository with a predefined set of entities.
	 * 
	 * @return
	 */
	@Bean
	public BlogPostInitializer initializer() {
		return new BlogPostInitializer(BLOG_POST_ATOM_FEED_SOURCE);
	}

	/**
	 * Clean up after execution by dropping used test db instance.
	 * 
	 * @throws Exception
	 */
	@PreDestroy
	public void dropTestDB() throws Exception {
		mongo().dropDatabase(getDatabaseName());
	}

}
