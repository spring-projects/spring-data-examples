/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.cassandra.projection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;

/**
 * Sample repository managing customers to show projecting functionality of Spring Data Cassandra.
 *
 * @author Mark Paluch
 */
interface CustomerRepository extends CrudRepository<Customer, String> {

	/**
	 * Uses a projection interface to indicate the fields to be returned. As the projection doesn't use any dynamic
	 * fields, the query execution will be restricted to only the fields needed by the projection.
	 *
	 * @return
	 */
	Collection<CustomerProjection> findAllProjectedBy();

	/**
	 * When a projection is used that contains dynamic properties (i.e. SpEL expressions in an {@link Value} annotation),
	 * the normal target entity will be loaded but dynamically projected so that the target can be referred to in the
	 * expression.
	 *
	 * @return
	 */
	Collection<CustomerSummary> findAllSummarizedBy();

	/**
	 * Passes in the projection type dynamically.
	 *
	 * @param id
	 * @param projection
	 * @return
	 */
	<T> Collection<T> findById(String id, Class<T> projection);

	/**
	 * Projection for a single entity.
	 *
	 * @param id
	 * @return
	 */
	CustomerProjection findProjectedById(String id);

	/**
	 * Dynamic projection for a single entity.
	 *
	 * @param id
	 * @param projection
	 * @return
	 */
	<T> T findProjectedById(String id, Class<T> projection);
}
