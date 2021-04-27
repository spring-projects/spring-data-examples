/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.rest.stores;

import static org.springframework.data.mongodb.core.index.GeoSpatialIndexType.*;

import lombok.Value;
import lombok.With;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity to represent a {@link Store}.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Value
@With
@Document
public class Store {

	@Id UUID id;
	String name;
	Address address;

	/**
	 * Value object to represent an {@link Address}.
	 *
	 * @author Oliver Gierke
	 */
	@Value
	public static class Address {

		String street, city, zip;
		@GeoSpatialIndexed(type = GEO_2DSPHERE) Point location;

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return String.format("%s, %s %s", street, zip, city);
		}
	}
}
