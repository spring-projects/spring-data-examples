/*
 * Copyright 2014-2018 the original author or authors.
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
import example.springdata.solr.product.ProductRepository;
import example.springdata.test.util.InfrastructureRule;

import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Christoph Strobl
 * @author Jens Schauder
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicSolrRepositoryTests {



	@Rule @Autowired public InfrastructureRule requiresRunningServer;

	@Autowired ProductRepository repository;

	/**
	 * Remove test data when context is shut down.
	 */
	public @After void deleteDocumentsOnShutdown() {
		repository.deleteAll();
	}

	/**
	 * Initialize Solr instance with test data once context has started.
	 */
	public @Before void initWithTestData() {

		repository.deleteAll();

		IntStream.range(0, 100)
				.forEach(index -> repository.save(Product.builder().id("p-" + index).name("foobar").build()));
	}

	/**
	 * Finds all entries using a single request.
	 */
	@Test
	public void findAll() {
		repository.findAll().forEach(System.out::println);
	}

	/**
	 * Pages through all entries using cursor marks. Have a look at the Solr console output to see iteration steps.
	 */
	@Test
	public void findAllUsingDeepPagination() {
		repository.findAllUsingCursor().forEachRemaining(System.out::println);
	}
}
