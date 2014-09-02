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

import java.util.Iterator;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	 * Finds all entries using a single request.
	 */
	@Test
	public void findAll() {

		Iterator<Product> iterator = repo.findAll().iterator();
		printResult(iterator);
	}

	/**
	 * Pages through all entries using cursor marks. Have a look at the Solr console output to see iteration steps.
	 */
	@Test
	public void findAllUsingDeepPagination() {

		Cursor<Product> cursor = repo.findAllUsingCursor();
		printResult(cursor);
	}

	private void printResult(Iterator<Product> it) {

		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
