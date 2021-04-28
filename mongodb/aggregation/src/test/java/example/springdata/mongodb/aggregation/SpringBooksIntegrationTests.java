/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.mongodb.aggregation;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.assertj.core.util.Files;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.BucketAutoOperation.Granularities;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Examples for Spring Books using the MongoDB Aggregation Framework. Data originates from Google's Book search.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @see <a href=
 *      "https://www.googleapis.com/books/v1/volumes?q=intitle:spring+framework">https://www.googleapis.com/books/v1/volumes?q=intitle:spring+framework</a>
 * @see <a href="/books.json>books.json</a>
 */
@SpringBootTest
class SpringBooksIntegrationTests {

	@Autowired MongoOperations operations;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void before() throws Exception {

		if (operations.count(new Query(), "books") == 0) {

			var file = new ClassPathResource("books.json").getFile();
			var content = Files.contentOf(file, StandardCharsets.UTF_8);
			var wrapper = Document.parse("{wrapper: " + content + "}");
			var books = wrapper.getList("wrapper", Object.class);

			operations.insert(books, "books");
		}
	}

	/**
	 * Project Book titles.
	 */
	@Test
	void shouldRetrieveOrderedBookTitles() {

		record BookTitle(String title) {
		}

		var aggregation = newAggregation( //
				sort(Direction.ASC, "volumeInfo.title"), //
				project().and("volumeInfo.title").as("title"));

		var result = operations.aggregate(aggregation, "books", BookTitle.class);

		assertThat(result.getMappedResults())//
				.extracting("title")//
				.containsSequence("Aprende a Desarrollar con Spring Framework", "Beginning Spring", "Beginning Spring 2");
	}

	/**
	 * Get number of books that were published by the particular publisher.
	 */
	@Test
	void shouldRetrieveBooksPerPublisher() {

		var aggregation = newAggregation( //
				group("volumeInfo.publisher") //
						.count().as("count"), //
				sort(Direction.DESC, "count"), //
				project("count").and("_id").as("publisher"));

		var result = operations.aggregate(aggregation, "books", BooksPerPublisher.class);

		assertThat(result).hasSize(27);
		assertThat(result).extracting("publisher").containsSequence("Apress", "Packt Publishing Ltd");
		assertThat(result).extracting("count").containsSequence(26, 22, 11);
	}

	/**
	 * Get number of books that were published by the particular publisher with their titles.
	 */
	@Test
	void shouldRetrieveBooksPerPublisherWithTitles() {

		var aggregation = newAggregation( //
				group("volumeInfo.publisher") //
						.count().as("count") //
						.addToSet("volumeInfo.title").as("titles"), //
				sort(Direction.DESC, "count"), //
				project("count", "titles").and("_id").as("publisher"));

		var result = operations.aggregate(aggregation, "books", BooksPerPublisher.class);

		var booksPerPublisher = result.getMappedResults().get(0);

		assertThat(booksPerPublisher.publisher()).isEqualTo("Apress");
		assertThat(booksPerPublisher.count()).isEqualTo(26);
		assertThat(booksPerPublisher.titles()).contains("Expert Spring MVC and Web Flow", "Pro Spring Boot");
	}

	/**
	 * Filter for Data-related books in their title and output the title and authors.
	 */
	@Test
	void shouldRetrieveDataRelatedBooks() {

		var aggregation = newAggregation( //
				match(Criteria.where("volumeInfo.title").regex("data", "i")), //
				replaceRoot("volumeInfo"), //
				project("title", "authors"), //
				sort(Direction.ASC, "title"));

		var result = operations.aggregate(aggregation, "books", BookAndAuthors.class);

		var bookAndAuthors = result.getMappedResults().get(1);

		assertThat(bookAndAuthors.title()).isEqualTo("Spring Data");
		assertThat(bookAndAuthors.authors()).contains("Mark Pollack", "Oliver Gierke", "Thomas Risberg", "Jon Brisbin",
				"Michael Hunger");
	}

	/**
	 * Retrieve the number of pages per author (and divide the number of pages by the number of authors).
	 */
	@Test
	void shouldRetrievePagesPerAuthor() {

		record PagesPerAuthor(@Id String author, int totalPageCount, int approxWritten) {
		}

		var aggregation = newAggregation( //
				match(Criteria.where("volumeInfo.authors").exists(true)), //
				replaceRoot("volumeInfo"), //
				project("authors", "pageCount") //
						.and(ArithmeticOperators.valueOf("pageCount") //
								.divideBy(ArrayOperators.arrayOf("authors").length()))
						.as("pagesPerAuthor"),
				unwind("authors"), //
				group("authors") //
						.sum("pageCount").as("totalPageCount") //
						.sum("pagesPerAuthor").as("approxWritten"), //
				sort(Direction.DESC, "totalPageCount"));

		var result = operations.aggregate(aggregation, "books", PagesPerAuthor.class);

		var pagesPerAuthor = result.getMappedResults().get(0);

		assertThat(pagesPerAuthor.author()).isEqualTo("Josh Long");
		assertThat(pagesPerAuthor.totalPageCount()).isEqualTo(1892);
		assertThat(pagesPerAuthor.approxWritten()).isEqualTo(573);
	}

	/**
	 * Categorize books by their page count into buckets.
	 */
	@Test
	void shouldCategorizeBooksInBuckets() {

		var aggregation = newAggregation( //
				replaceRoot("volumeInfo"), //
				match(Criteria.where("pageCount").exists(true)),
				bucketAuto("pageCount", 10) //
						.withGranularity(Granularities.SERIES_1_2_5) //
						.andOutput("title").push().as("titles") //
						.andOutput("titles").count().as("count"));

		var result = operations.aggregate(aggregation, "books", BookFacetPerPage.class);

		var mappedResults = result.getMappedResults();

		var facet_20_to_100_pages = mappedResults.get(0);
		assertThat(facet_20_to_100_pages.id().min()).isEqualTo(20);
		assertThat(facet_20_to_100_pages.id().max()).isEqualTo(100);
		assertThat(facet_20_to_100_pages.count()).isEqualTo(12);

		var facet_100_to_500_pages = mappedResults.get(1);
		assertThat(facet_100_to_500_pages.id().min()).isEqualTo(100);
		assertThat(facet_100_to_500_pages.id().max()).isEqualTo(500);
		assertThat(facet_100_to_500_pages.count()).isEqualTo(63);
		assertThat(facet_100_to_500_pages.titles()).contains("Spring Data");
	}

	/**
	 * Run a multi-faceted aggregation to get buckets by price (1-10, 10-50, 50-100 EURO) and by the first letter of the
	 * author name.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void shouldCategorizeInMultipleFacetsByPriceAndAuthor() {

		var aggregation = newAggregation( //
				match(Criteria.where("volumeInfo.authors").exists(true).and("volumeInfo.publisher").exists(true)),
				facet() //
						.and(match(Criteria.where("saleInfo.listPrice").exists(true)), //
								replaceRoot("saleInfo"), //
								bucket("listPrice.amount") //
										.withBoundaries(1, 10, 50, 100))
						.as("prices") //

						.and(unwind("volumeInfo.authors"), //
								replaceRoot("volumeInfo"), //
								match(Criteria.where("authors").not().size(0)), //
								project() //
										.andExpression("substrCP(authors, 0, 1)").as("startsWith") //
										.and("authors").as("author"), //
								bucketAuto("startsWith", 10) //
										.andOutput("author").push().as("authors") //
						).as("authors"));

		var result = operations.aggregate(aggregation, "books", Document.class);

		var uniqueMappedResult = result.getUniqueMappedResult();

		assertThat((List<Object>) uniqueMappedResult.get("prices")).hasSize(3);
		assertThat((List<Object>) uniqueMappedResult.get("authors")).hasSize(8);
	}

	private record BooksPerPublisher(String publisher, int count, List<String> titles) {
	}

	private record BookAndAuthors(String title, List<String> authors) {
	}

	private record BookFacetPerPage(BookFacetPerPageId id, int count, List<String> titles) {
	}

	private record BookFacetPerPageId(int min, int max) {
	}
}
