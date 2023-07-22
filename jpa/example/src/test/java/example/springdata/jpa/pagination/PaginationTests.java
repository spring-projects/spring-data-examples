/*
 * Copyright 2023 the original author or authors.
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
package example.springdata.jpa.pagination;

import static org.assertj.core.api.Assertions.*;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Window;
import org.springframework.data.support.WindowIterator;
import org.springframework.data.util.Streamable;

import com.github.javafaker.Faker;

/**
 * Show different types of paging styles using {@link Page}, {@link Slice} and {@link Window}.
 *
 * @author Christoph Strobl
 * @author Mark Paluch
 */
@DataJpaTest
class PaginationTests {

	@Configuration
	@EnableAutoConfiguration
	static class Config {

	}

	@Autowired BookRepository books;

	@BeforeEach
	void setUp() {

		Faker faker = new Faker();

		// create some sample data
		List<Author> authorList = createAuthors(faker);
		createBooks(faker, authorList);
	}

	/**
	 * Page through the results using an offset/limit approach where the server skips over the number of results specified
	 * via {@link Pageable#getOffset()}. The {@link Page} return type will run an additional {@literal count} query to
	 * read the total number of matching rows on each request.
	 */
	@Test
	void pageThroughResultsWithSkipAndLimit() {

		Page<Book> page;
		Pageable pageRequest = PageRequest.of(0, 2);

		do {

			page = books.findByTitleContainsOrderByPublicationDate("the", pageRequest);
			assertThat(page.getContent().size()).isGreaterThanOrEqualTo(1).isLessThanOrEqualTo(2);

			pageRequest = page.nextPageable();
		} while (page.hasNext());
	}

	/**
	 * Run through the results using an offset/limit approach where the server skips over the number of results specified
	 * via {@link Pageable#getOffset()}. No additional {@literal count} query to read the total number of matching rows is
	 * issued. Still {@link Slice} requests, but does not emit, one row more than specified via {@link Page#getSize()} to
	 * feed {@link Slice#hasNext()}
	 */
	@Test
	void sliceThroughResultsWithSkipAndLimit() {

		Slice<Book> slice;
		Pageable pageRequest = PageRequest.of(0, 2);

		do {

			slice = books.findBooksByTitleContainsOrderByPublicationDate("the", pageRequest);
			assertThat(slice.getContent().size()).isGreaterThanOrEqualTo(1).isLessThanOrEqualTo(2);

			pageRequest = slice.nextPageable();
		} while (slice.hasNext());
	}

	/**
	 * Scroll through the results using an offset/limit approach where the server skips over the number of results
	 * specified via {@link OffsetScrollPosition#getOffset()}.
	 * <p>
	 * This approach is similar to the {@link #sliceThroughResultsWithSkipAndLimit() slicing one}.
	 */
	@Test
	void scrollThroughResultsWithSkipAndLimit() {

		Window<Book> window;
		ScrollPosition scrollPosition = ScrollPosition.offset();

		do {

			window = books.findTop2ByTitleContainsOrderByPublicationDate("the", scrollPosition);
			assertThat(window.getContent().size()).isGreaterThanOrEqualTo(1).isLessThanOrEqualTo(2);

			scrollPosition = window.positionAt(window.getContent().size() - 1);
		} while (window.hasNext());
	}

	/**
	 * Scroll through the results using an offset/limit approach where the server skips over the number of results
	 * specified via {@link OffsetScrollPosition#getOffset()} using {@link WindowIterator}.
	 * <p>
	 * This approach is similar to the {@link #sliceThroughResultsWithSkipAndLimit() slicing one}.
	 */
	@Test
	void scrollThroughResultsUsingWindowIteratorWithSkipAndLimit() {

		WindowIterator<Book> iterator = WindowIterator
				.of(scrollPosition -> books.findTop2ByTitleContainsOrderByPublicationDate("the-crazy-book-", scrollPosition))
				.startingAt(ScrollPosition.offset());

		List<Book> allBooks = Streamable.of(() -> iterator).stream().toList();
		assertThat(allBooks).hasSize(50);
	}

	/**
	 * Scroll through the results using an index based approach where the {@link KeysetScrollPosition#getKeys() keyset}
	 * keeps track of already seen values to resume scrolling by altering the where clause to only return rows after the
	 * values contained in the keyset. Set {@literal logging.level.org.hibernate.SQL=debug} to show the modified query in
	 * the log.
	 */
	@Test
	void scrollThroughResultsWithKeyset() {

		Window<Book> window;
		ScrollPosition scrollPosition = ScrollPosition.keyset();
		do {

			window = books.findTop2ByTitleContainsOrderByPublicationDate("the", scrollPosition);
			assertThat(window.getContent().size()).isGreaterThanOrEqualTo(1).isLessThanOrEqualTo(2);

			scrollPosition = window.positionAt(window.getContent().size() - 1);
		} while (window.hasNext());
	}

	// --> Test Data

	@Autowired EntityManager em;

	private List<Author> createAuthors(Faker faker) {

		List<Author> authors = IntStream.range(0, 10).mapToObj(id -> {

			Author author = new Author();
			author.setId("author-%s".formatted(id));
			author.setFirstName(faker.name().firstName());
			author.setLastName(faker.name().lastName());

			em.persist(author);
			return author;
		}).collect(Collectors.toList());
		return authors;
	}

	private List<Book> createBooks(Faker faker, List<Author> authors) {

		Random rand = new Random();
		return IntStream.range(0, 100).mapToObj(id -> {

			Book book = new Book();
			book.setId("book-%03d".formatted(id));
			book.setTitle((id % 2 == 0 ? "the-crazy-book-" : "") + faker.book().title());
			book.setIsbn10(UUID.randomUUID().toString().substring(0, 10));
			book.setPublicationDate(faker.date().past(5000, TimeUnit.DAYS));
			book.setAuthor(authors.get(rand.nextInt(authors.size())));

			em.persist(book);
			return book;
		}).collect(Collectors.toList());
	}
}
