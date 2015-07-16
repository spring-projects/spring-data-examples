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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.Cursor;

/**
 * Implementation of {@link ProductRepositoryCustom}.
 * 
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
class ProductRepositoryImpl implements ProductRepositoryCustom {

	@Autowired SolrTemplate solrTemplate;

	/*
	 * (non-Javadoc)
	 * @see example.springdata.solr.ProductRepositoryCustom#findAllUsingCursor()
	 */
	@Override
	public Cursor<Product> findAllUsingCursor() {

		// NOTE: Using Cursor requires to sort by an unique field
		return solrTemplate.queryForCursor(new SimpleQuery("*:*").addSort(new Sort("id")), Product.class);
	}
}
