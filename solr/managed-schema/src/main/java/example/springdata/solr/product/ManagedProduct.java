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
package example.springdata.solr.product;

import java.util.List;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Document representing a Product and its attributes that are propagated to the solr schema on first save of entity.
 * 
 * @author Christoph Strobl
 */
@SolrDocument(solrCoreName = "collection1")
@Data
public class ManagedProduct {

	private @Id String id;
	private @Indexed(type = "text_general") String name;
	private @Indexed(name = "cat", type = "string") List<String> category;
	private @Indexed boolean inStock;

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", inStock=" + inStock + "]";
	}
}
