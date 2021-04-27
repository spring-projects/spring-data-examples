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

import lombok.ToString;

import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

/**
 * One of potentially multiple models that can be build from a single {@link LegoSet}.
 * <p>
 * No getters or setters needed.
 *
 * @author Jens Schauder
 */
@ToString
public class Model implements Persistable<String> {

	String name, description;

	@Nullable
	@Override
	public String getId() {
		return name;
	}

	@Override
	public boolean isNew() {
		return true;
	}
}
