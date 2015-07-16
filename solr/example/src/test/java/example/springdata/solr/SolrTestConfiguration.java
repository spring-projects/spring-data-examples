/*
 * Copyright 2014-2015 the original author or authors.
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
package example.springdata.solr;

import example.springdata.solr.product.Product;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @author Christoph Strobl
 */
@Configuration
@EnableAutoConfiguration
public class SolrTestConfiguration {

	private @Autowired CrudRepository<Product, String> repo;

	@Bean
	public SolrTemplate solrTemplate() {
		return new SolrTemplate(new HttpSolrServer("http://localhost:8983/solr"), "collection1");
	}

	/**
	 * Remove test data when context is shut down.
	 */
	@PreDestroy
	public void deleteDocumentsOnShutdown() {
		repo.deleteAll();
	}

	/**
	 * Initialize Solr instance with test data once context has started.
	 */
	@PostConstruct
	public void initWithTestData() {
		doInitTestData(repo);
	}

	protected void doInitTestData(CrudRepository<Product, String> repository) {

		for (int i = 0; i < 100; i++) {
			repository.save(Product.builder().id("p-" + i).name("foobar").build());
		}
	}
}
