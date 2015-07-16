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

import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;
import static org.springframework.data.solr.core.query.Criteria.*;
import static org.springframework.data.solr.core.query.ExistsFunction.*;

import java.util.Arrays;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Function;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Boost;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.springdata.solr.product.Product;
import example.springdata.solr.product.ProductRepository;
import example.springdata.solr.test.util.RequiresSolrServer;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AdvancedSolrRepositoryTests {

	public static @ClassRule RequiresSolrServer requiresRunningServer = RequiresSolrServer.onLocalhost();

	@Configuration
	static class Config extends SolrTestConfiguration {

		@Override
		protected void doInitTestData(CrudRepository<Product, String> repository) {

			Product playstation = Product.builder().id("id-1").name("Playstation")
					.description("The Sony playstation was the top selling gaming system in 1994.").popularity(5).build();
			Product playstation2 = Product.builder().id("id-2").name("Playstation Two")
					.description("Playstation two is the successor of playstation in 2000.").build();
			Product superNES = Product.builder().id("id-3").name("Super Nintendo").popularity(3).build();
			Product nintendo64 = Product.builder().id("id-4").name("N64").description("Nintendo 64").popularity(2).build();

			repository.save(Arrays.asList(playstation, playstation2, superNES, nintendo64));
		}
	}

	@Autowired ProductRepository repository;
	@Autowired SolrOperations operations;

	/**
	 * {@link HighlightPage} holds next to the entities found also information about where a match was found within the
	 * document. This allows to fine grained display snipplets of data containing the matching term in context.
	 */
	@Test
	public void annotationBasedHighlighting() {

		HighlightPage<Product> products = repository.findByDescriptionStartingWith("play", new PageRequest(0, 10));

		products.getHighlighted().forEach(
				entry -> entry.getHighlights().forEach(
						highligh -> System.out.println(entry.getEntity().getId() + " | " + highligh.getField() + ":\t"
								+ highligh.getSnipplets())));
	}

	/**
	 * Using {@link Boost} allows to influence scoring at query time. In this case we want hits in {@code Product#name} to
	 * count twice as much as such in {@code Product#description}.
	 */
	@Test
	public void annotationBasedBoosting() {
		repository.findTop10ByNameOrDescription("Nintendo", "Nintendo").forEach(System.out::println);
	}

	/**
	 * Using {@link Function} in queries has no influence on restricting results as all documents will match the function.
	 * Though it does influence document score. In this sample documents not having popularity assigned will be sorted to
	 * the end of the list.
	 */
	@Test
	public void influcenceScoreWithFunctions() {

		Query query = new SimpleQuery(where(exists("popularity"))).addProjectionOnFields("*", "score");

		operations.queryForPage(query, Product.class).forEach(System.out::println);
	}

	/**
	 * Using {@link SolrOperations#getById(java.io.Serializable, Class)} allows reading uncommitted documents from the
	 * update log.
	 */
	@Test
	public void useRealtimeGetToReadUncommitedDocuments() throws InterruptedException {

		Product xbox = Product.builder().id("id-5").name("XBox").description("Microsift XBox").popularity(2).build();
		Query query = new SimpleQuery(where("id").is(xbox.getId()));

		// add document but delay commit for 3 seconds
		operations.saveBean(xbox, 3000);

		// document will not be returned hence not yet committed to the index
		assertThat(operations.queryForObject(query, Product.class), nullValue());

		// realtime-get fetches uncommitted document
		assertThat(operations.getById(xbox.getId(), Product.class), notNullValue());

		// wait a little so that changes get committed to the index - normal query will now be able to find the document.
		Thread.sleep(3010);
		assertThat(operations.queryForObject(query, Product.class), notNullValue());
	}
}
