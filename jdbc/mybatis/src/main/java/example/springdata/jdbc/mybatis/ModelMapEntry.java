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
package example.springdata.jdbc.mybatis;

import lombok.Getter;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Jens Schauder
 */
@Getter
public class ModelMapEntry implements Map.Entry<String, Model> {

	private final String key;
	private final Model value;

	ModelMapEntry(String name, Clob description) {

		key = name;
		value = new Model();
		value.name = name;

		try {
			value.description = description.getSubString(1, (int) description.length());
		} catch (SQLException se) {
			throw new RuntimeException(se);
		}
	}

	@Override
	public Model setValue(Model value) {
		throw new UnsupportedOperationException("can't set the value of a ModelMapEntry");
	}
}
