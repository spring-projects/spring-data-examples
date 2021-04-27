/*
 * Copyright 2021-2021 the original author or authors.
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
package example.springdata.mongodb.unwrapping;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Christoph Strobl
 */
public interface UserRepository extends CrudRepository<User, String> {

	/**
	 * Use the value type directly. This will unwrap the values into the target query object.
	 */
	User findByUserName(UserName userName);

	/**
	 * Use one of the unwrapped properties. This will drill into the unwrapped using the value for the target query.
	 */
	User findByEmailEmail(String email);
}
