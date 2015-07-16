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

import lombok.Builder;
import lombok.Value;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Score;

/**
 * Document representing a {@link Product} and its attributes matching the fields defined in the <a
 * href="http://localhost:8983/solr/collection1/schema">example solr schema</a>.
 * 
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Value
@Builder
@SolrDocument(solrCoreName = "collection1")
public class Product {

	private @Id String id;
	private @Indexed String name;
	private @Indexed(name = "cat") List<String> category;
	private @Indexed(name = "store") Point location;
	private @Indexed String description;
	private @Indexed boolean inStock;
	private @Indexed Integer popularity;
	private @Score Float score;
}
