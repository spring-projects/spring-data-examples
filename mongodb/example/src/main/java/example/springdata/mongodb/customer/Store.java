/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.mongodb.customer;

import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

/**
 * An entity to represent a Store with a service area.
 *
 * @author Rishabh Saraswat
 */
@Data
@Document
public class Store {

	private String id;
	private final String name;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private final GeoJsonPolygon serviceArea; // unlike Polygon, GeoJsonPolygon is closed boundary

	/**
	 * Creates a new {@link Store} with the given name and service area.
	 *
	 * @param name        must not be {@literal null} or empty.
	 * @param serviceArea must not be {@literal null}.
	 */
	public Store(String name, GeoJsonPolygon serviceArea) {
		Assert.hasText(name, "Name must not be empty");
		Assert.notNull(serviceArea, "Service area must not be null");
		this.name = name;
		this.serviceArea = serviceArea;
	}
}