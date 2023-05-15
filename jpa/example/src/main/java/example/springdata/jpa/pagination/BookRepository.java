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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Window;
import org.springframework.data.repository.ListCrudRepository;

/**
 * @author Christoph Strobl
 */
public interface BookRepository extends ListCrudRepository<Book, String> {

	/**
	 * Uses an {@literal offset} based pagination that first sorts the entries by their {@link Book#getPublicationDate()
	 * publication_date} and then limits the result by dropping the number of rows specified in the
	 * {@link Pageable#getOffset() offset} clause. To retrieve {@link Page#getTotalElements()} an additional count query
	 * is executed.
	 *
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Book> findByTitleContainsOrderByPublicationDate(String title, Pageable pageable);

	/**
	 * Uses an {@literal offset} based slicing that first sorts the entries by their {@link Book#getPublicationDate()
	 * publication_date} and then limits the result by dropping the number of rows specified in the
	 * {@link Pageable#getOffset() offset} clause.
	 *
	 * @param title
	 * @param pageable
	 * @return
	 */
	Slice<Book> findBooksByTitleContainsOrderByPublicationDate(String title, Pageable pageable);

	/**
	 * Depending on the provided {@link ScrollPosition} either {@link org.springframework.data.domain.OffsetScrollPosition
	 * offset} or {@link org.springframework.data.domain.KeysetScrollPosition keyset} scrolling is possible. Scrolling
	 * through results requires a stable {@link org.springframework.data.domain.Sort} which is different from what
	 * {@link Pageable#getSort()} offers. The {@literal limit} is defined via the {@literal Top} keyword.
	 *
	 * @param title
	 * @param scrollPosition
	 * @return
	 */
	Window<Book> findTop2ByTitleContainsOrderByPublicationDate(String title, ScrollPosition scrollPosition);
}
