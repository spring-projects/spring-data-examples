/*
 * Copyright 2015 the original author or authors.
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
package example.springdata.jpa.customall;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * @author Oliver Gierke
 * @soundtrack Elen - Nobody Else (Elen)
 */
class ExtendedJpaRepository<T> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {

	/**
	 * Creates a new {@link ExtendedJpaRepository} for the given {@link JpaEntityInformation} and {@link EntityManager}.
	 * 
	 * @param entityInformation must not be {@literal null}.
	 * @param entityManager must not be {@literal null}.
	 */
	public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	/* 
	 * (non-Javadoc)
	 * @see example.springdata.jpa.customall.BaseRepository#customMethod()
	 */
	@Override
	public long customMethod() {
		return 0;
	}
}
