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
package example.springdata.r2dbc.basics;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Simple repository interface for {@link Person} instances. The interface implements
 * {@link ReactiveQueryByExampleExecutor} and allows execution of methods accepting
 * {@link org.springframework.data.domain.Example}.
 *
 * @author Mark Paluch
 */
interface PersonRepository extends ReactiveCrudRepository<Person, String>, ReactiveQueryByExampleExecutor<Person> {}
