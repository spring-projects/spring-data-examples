/*
 * Copyright 2014-2018 the original author or authors.
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
package example.springdata.solr;

import example.springdata.solr.product.Product;
import example.springdata.solr.test.util.SolrInfrastructureRule;
import example.springdata.test.util.InfrastructureRule;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @author Jens Schauder
 */
@SpringBootApplication
public class SolrTestConfiguration {

	@Autowired CrudRepository<Product, String> repo;

	private static final Logger LOG = LoggerFactory.getLogger(SolrTestConfiguration.class);

	public @Bean InfrastructureRule<String> infrastructureRule() {

		return new SolrInfrastructureRule("techproducts");
	}

	public @Bean SolrTemplate solrTemplate() {
		return new SolrTemplate(new HttpSolrClient.Builder().withBaseSolrUrl(infrastructureRule().getInfo()).build());
	}

}
