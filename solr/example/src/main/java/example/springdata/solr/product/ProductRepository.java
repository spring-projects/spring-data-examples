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
package example.springdata.solr.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Boost;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;

/**
 * Repository definition for {@link Product}.
 * 
 * @author Christoph Strobl
 */
public interface ProductRepository extends ProductRepositoryCustom, CrudRepository<Product, String> {

	/**
	 * Find documents with matching description, highlighting context within a 20 char range around the hit.
	 * 
	 * @param description
	 * @param page
	 * @return
	 */
	@Highlight(fragsize = 20, snipplets = 3)
	HighlightPage<Product> findByDescriptionStartingWith(String description, Pageable page);

	/**
	 * Find the first 10 documents with a match in name or description. Boosting score for search hits in name by 2 sorts
	 * documents by relevance.
	 * 
	 * @param name
	 * @param description
	 * @return
	 */
	@Query
	List<Product> findTop10ByNameOrDescription(@Boost(2) String name, String description);
}
