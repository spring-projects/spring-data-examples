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
package example.springdata.solr;

import example.springdata.solr.product.ProductRepository;

import javax.annotation.PreDestroy;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * {@link Configuration} class enabling schema support for solr.<br />
 * <br />
 * <strong>NOTE</strong>: Requires solr to run in managed schema mode. run with
 * {@code solr/example $ java -Dsolr.solr.home=example-schemaless/solr -jar start.jar}.
 * 
 * @author Christoph Strobl
 */
@Configuration
@EnableSolrRepositories(schemaCreationSupport = true, multicoreSupport = true)
public class SolrTestConfiguration {

	@Autowired ProductRepository repo;

	@Bean
	public SolrServer solrServer() {
		return new HttpSolrServer("http://localhost:8983/solr");
	}

	/**
	 * Remove test data when context is shut down.
	 */
	@PreDestroy
	public void deleteDocumentsOnShutdown() {
		repo.deleteAll();
	}
}
