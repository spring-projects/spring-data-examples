/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.cassandra.optimisticlocking;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Simple domain object that declares properties annotated with Spring Data's {@code @Version} annotation to enable the
 * object for optimistic locking.
 *
 * @author Mark Paluch
 */
@Table
public record OptimisticPerson(@Id Long id, @Version long version, String name) {

	public OptimisticPerson withName(String name) {
		return new OptimisticPerson(id, version, name);
	}
}
