/*
 * Copyright 2015 the original author or authors.
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
package example.springdata.mongodb.geojson;

import java.util.List;

import org.springframework.data.geo.Polygon;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data repository interface to manage {@link Store} instances.
 * 
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
interface StoreRepository extends CrudRepository<Store, String> {

	/**
	 * Returns all {@link Store}s located withing the given {@link Polygon}.
	 * 
	 * @param polygon must not be {@literal null}.
	 * @return
	 */
	List<Store> findByLocationWithin(Polygon polygon);
}
