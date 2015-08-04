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
package example.springdata.rest.stores;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.mysema.query.types.path.StringPath;

/**
 * Repository interface for out-of-the-box paginating access to {@link Store}s and a query method to find stores by
 * location and distance.
 * 
 * @author Oliver Gierke
 */
public interface StoreRepository extends PagingAndSortingRepository<Store, String>, QueryDslPredicateExecutor<Store>,
		QuerydslBinderCustomizer<QStore> {

	@RestResource(rel = "by-location")
	Page<Store> findByAddressLocationNear(Point location, Distance distance, Pageable pageable);

	/**
	 * Tweak the Querydsl binding if collection resources are filtered.
	 * 
	 * @see org.springframework.data.web.querydsl.QuerydslBinderCustomizer#customize(org.springframework.data.web.querydsl.QuerydslBindings,
	 *      com.mysema.query.types.EntityPath)
	 */
	default void customize(QuerydslBindings bindings, QStore store) {

		bindings.bind(store.address.city).first((path, value) -> path.endsWith(value));
		bindings.bind(String.class).first((StringPath path, String value) -> path.contains(value));
	}
}
