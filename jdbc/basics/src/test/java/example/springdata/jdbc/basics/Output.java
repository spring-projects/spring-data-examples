/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.jdbc.basics;

import lombok.experimental.UtilityClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trivial class to print domain objects to the console in a somewhat readable format.
 *
 * @author Jens Schauder
 */
@UtilityClass
public class Output {

	private final Logger LOG = LoggerFactory.getLogger(Output.class);

	public static void list(Iterable<?> categories, String title) {

		var message = new StringBuilder(String.format("==== %s ====\n", title));

		categories.forEach(category -> message.append(category.toString().replace(", ", ",\n\t")));

		LOG.info(message.toString());
	}
}
