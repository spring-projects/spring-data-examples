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
package example.springdata.mongodb;

import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The domain type of choice.
 * <p />
 * This time <a href="http://www.cbs.com/shows/star-trek-discovery/">Star Trek: Discovery</a> <br />
 * <i>Just a trekkie - no relation to CBS in any way other than watching.</i>
 *
 * @author Christoph Strobl
 */
record Person(@Id ObjectId id, @Field("first_name") String firstname, @Field("last_name") String lastname, int age) {
	public Person(String firstname, String lastname, int age) {
		this(null, firstname, lastname, age);
	}
}
