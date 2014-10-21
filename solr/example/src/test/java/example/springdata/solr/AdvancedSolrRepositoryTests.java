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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import example.springdata.solr.test.util.RequiresSolrServer;

/**
 * @author Christoph Strobl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AdvancedSolrRepositoryTests {

	public static @ClassRule RequiresSolrServer requiresRunningServer = RequiresSolrServer.onLocalhost();

	@Configuration
	static class Config extends SolrTestConfiguration {

		@Override
		protected void doInitTestData(CrudRepository<Product, String> repository) {

			Product playstation = new ProductBuilder().withId("id-1").named("Playstation")
					.withDescription("The Sony playstation was the top selling gaming system in 1994.").withPopularity(5).build();

			Product playstation2 = new ProductBuilder().withId("id-2").named("Playstation Two")
					.withDescription("Playstation two is the successor of playstation in 2000.").build();

			Product superNES = new ProductBuilder().withId("id-3").named("Super Nintendo").withPopularity(3).build();

			Product nintendo64 = new ProductBuilder().withId("id-4").named("N64").withDescription("Nintendo 64")
					.withPopularity(2).build();

			repository.save(Arrays.asList(playstation, playstation2, superNES, nintendo64));
		}
	}

	@Autowired ProductRepository repo;
	@Autowired SolrOperations operations;

	/**
	 * {@link HighlightPage} holds next to the entities found also information about where a match was found within the
	 * document. This allows to fine grained display snipplets of data containing the matching term in context.
	 */
	@Test
	public void annotationBasedHighlighting() {

		HighlightPage<Product> products = repo.findByDescriptionStartingWith("play", new PageRequest(0, 10));

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

		repo.findTop10ByNameOrDescription("Nintendo", "Nintendo") //
				.forEach(System.out::println);
	}

	/**
	 * Using {@link Function} in queries has no influence on restricting results as all documents will match the function.
	 * Though it does influence document score. In this sample documents not having popularity assigned will be sorted to
	 * the end of the list.
	 */
	@Test
	public void influcenceScoreWithFunctions() {

		operations.queryForPage(new SimpleQuery(where(exists("popularity"))).addProjectionOnFields("*", "score"),
				Product.class) //
				.forEach(System.out::println);
	}

	/**
	 * Using {@link SolrOperations#getById(java.io.Serializable, Class)} allows reading uncommitted documents from the
	 * update log.
	 */
	@Test
	public void useRealtimeGetToReadUncommitedDocuments() throws InterruptedException {

		Product xbox = new ProductBuilder().withId("id-5").named("XBox").withDescription("Microsift XBox")
				.withPopularity(2).build();
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

	static class ProductBuilder {

		private Product product;

		public ProductBuilder() {
			this.product = new Product();
		}

		public ProductBuilder withId(String id) {
			this.product.setId(id);
			return this;
		}

		public ProductBuilder named(String name) {
			this.product.setName(name);
			return this;
		}

		public ProductBuilder withDescription(String description) {
			this.product.setDescription(description);
			return this;
		}

		public ProductBuilder withPopularity(Integer popularity) {
			this.product.setPopularity(popularity);
			return this;
		}

		public ProductBuilder inCategory(String category) {

			List<String> categories = new ArrayList<>();
			categories.add(category);

			if (this.product.getCategory() == null) {
				categories.addAll(this.product.getCategory());
			}

			this.product.setCategory(categories);
			return this;

		}

		public Product build() {
			return this.product;
		}

	}
}
