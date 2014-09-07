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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Configuration
@EnableAutoConfiguration
public class MongoTestConfiguration {

	@Autowired MongoOperations operations;

	@Bean
	public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

		Jackson2RepositoryPopulatorFactoryBean factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
		factoryBean.setResources(new Resource[] { new ClassPathResource("spring-blog.atom.json") });
		return factoryBean;
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
