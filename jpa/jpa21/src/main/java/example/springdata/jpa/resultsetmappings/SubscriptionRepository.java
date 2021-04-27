/*
 * Copyright 2018-2021 the original author or authors.
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
package example.springdata.jpa.resultsetmappings;

import java.util.List;

import javax.persistence.SqlResultSetMapping;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Thomas Darimont
 */
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

	/**
	 * Returns an aggregated {@link SubscriptionSummary} by Product.
	 * <p>
	 * Note that this example uses a JPA 2.1 Constructor based {@link javax.persistence.SqlResultSetMapping} in
	 * combination with native query defined in {@link Subscription}.
	 *
	 * @return
	 */
	@Query(nativeQuery = true)
	List<SubscriptionSummary> findAllSubscriptionSummaries();

	/**
	 * Returns an aggregated {@link SubscriptionProjection} using a named native query but does not require a dedicated
	 * {@link SqlResultSetMapping}.
	 * 
	 * @return
	 */
	@Query(nativeQuery = true)
	List<SubscriptionProjection> findAllSubscriptionProjections();
}
