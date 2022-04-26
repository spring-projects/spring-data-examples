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

import org.bson.Document;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.convert.MongoConversionContext;
import org.springframework.data.mongodb.core.convert.MongoValueConverter;

/**
 * {@link MongoValueConverter} to write only the {@link Address#location} into a MongoDB {@link Document}.
 *
 * @author Mark Paluch
 */
class AddressDocumentConverter implements MongoValueConverter<Address, Document> {

	@Override
	public Address read(Document value, MongoConversionContext context) {

		Point point = new Point(context.read(value.get("x"), Double.class), context.read(value.get("y"), Double.class));

		return new Address(point);
	}

	@Override
	public Document write(Address value, MongoConversionContext context) {

		Document document = new Document();
		document.put("x", value.getLocation().getX());
		document.put("y", value.getLocation().getY());

		return document;
	}
}
