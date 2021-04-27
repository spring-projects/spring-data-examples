/*
 * Copyright 2015-2021 the original author or authors.
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
package example.users;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.types.dsl.StringPath;

/**
 * Repository to manage {@link User}s. Also implements {@link QueryDslPredicateExecutor} to enable predicate filtering
 * on Spring MVC controllers as well as {@link QuerydslBinderCustomizer} to tweak the way predicates are created for
 * properties.
 *
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
public interface UserRepository
		extends CrudRepository<User, String>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.querydsl.binding.QuerydslBinderCustomizer#customize(org.springframework.data.querydsl.binding.QuerydslBindings, com.mysema.query.types.EntityPath)
	 */
	@Override
	default public void customize(QuerydslBindings bindings, QUser root) {

		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		bindings.excluding(root.password);
	}
}
