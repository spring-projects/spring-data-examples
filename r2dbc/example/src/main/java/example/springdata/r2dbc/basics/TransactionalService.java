/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.r2dbc.basics;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Drotbohm
 */
@Component
@RequiredArgsConstructor
class TransactionalService {

	private final @NonNull CustomerRepository repository;

	/**
	 * Saves the given {@link Customer} unless its firstname is "Dave".
	 *
	 * @param customer must not be {@literal null}.
	 * @return
	 */
	@Transactional
	public Mono<Customer> save(Customer customer) {

		return repository.save(customer).map(it -> {

			if (it.firstname().equals("Dave")) {
				throw new IllegalStateException();
			} else {
				return it;
			}
		});
	}
}
