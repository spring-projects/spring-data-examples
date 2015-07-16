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

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.springdata.solr.product.ManagedProduct;
import example.springdata.solr.product.ProductRepository;
import example.springdata.solr.test.util.RequiresSolrServer;

/**
 * @author Christoph Strobl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SolrTestConfiguration.class })
public class SolrRepositoryTests {

	public static @ClassRule RequiresSolrServer requiresRunningServer = RequiresSolrServer.onLocalhost();

	@Autowired ProductRepository repo;

	/**
	 * Adds missing fields to the schema. <br />
	 * By default the fields {@literal id} and {@literal _version_} are present. <br />
	 * Check fields using <a
	 * href="http://localhost:8983/solr/collection1/schema/fields">../solr/collection1/schema/fields</a> <br />
	 * <br />
	 * <strong>NOTE</strong>: requires Solr to run in managed schema mode.
	 */
	@Test
	public void triggerSchemaUpdateOnFirstSave() {

		ManagedProduct p = new ManagedProduct();
		p.setId("p-1");
		repo.save(p);

		Iterable<ManagedProduct> all = repo.findAll();
		for (ManagedProduct product : all) {
			System.out.println(product);
		}
	}
}
