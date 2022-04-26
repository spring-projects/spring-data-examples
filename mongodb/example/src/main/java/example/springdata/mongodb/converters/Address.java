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
package example.springdata.mongodb.converters;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A domain object to capture addresses.
 *
 * @author Mark Paluch
 */
@Data
@RequiredArgsConstructor
public class Address {

	private final Point location;
	private String street;
	private String zipCode;

	Address(@JsonProperty("location") Map<String, Double> location, @JsonProperty("street") String street,
			@JsonProperty("zipCode") String zipCode) {

		this.location = new Point(location.get("x"), location.get("y"));
		this.street = street;
		this.zipCode = zipCode;
	}

}
