/*
 * Copyright 2022 the original author or authors.
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
package example.springdata.mongodb.converters;

import org.springframework.data.mongodb.core.convert.MongoConversionContext;
import org.springframework.data.mongodb.core.convert.MongoValueConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link MongoValueConverter} to write {@link Address} into JSON through Jackson.
 *
 * @author Mark Paluch
 */
class AddressJsonConverter implements MongoValueConverter<Address, String> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public Address read(String value, MongoConversionContext context) {

		try {
			return mapper.readValue(value, Address.class);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Cannot unmarshal Address JSON", e);
		}
	}

	@Override
	public String write(Address value, MongoConversionContext context) {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Cannot marshal Address into JSON", e);
		}
	}
}
