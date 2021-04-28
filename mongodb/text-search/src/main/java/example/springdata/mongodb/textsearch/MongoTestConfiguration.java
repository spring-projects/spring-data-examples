/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.mongodb.textsearch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Configuration
@SpringBootApplication
public class MongoTestConfiguration {

	@Autowired MongoOperations operations;

	public @Bean Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

		var factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
		factoryBean.setResources(new Resource[] { new ClassPathResource("spring-blog.atom.json") });
		return factoryBean;
	}

	@PostConstruct
	private void postConstruct() {

		var resolver = IndexResolver.create(operations.getConverter().getMappingContext());

		resolver.resolveIndexFor(BlogPost.class).forEach(operations.indexOps(BlogPost.class)::ensureIndex);
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
