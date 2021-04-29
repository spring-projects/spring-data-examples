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
package example.springdata.jpa.compositions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

/**
 * Generic repository extension for JPA repositories to flush after each save.
 *
 * @author Mark Paluch
 */
@RequiredArgsConstructor
public class FlushOnSaveRepositoryImpl<T> implements FlushOnSaveRepository<T> {

	final @NonNull EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.compositions.FlushOnSaveRepository#save(java.lang.Object)
	 */
	@Transactional
	@Override
	public <S extends T> S save(S entity) {

		doSave(entity);

		entityManager.flush();

		return entity;
	}

	/**
	 * @param entity
	 */
	private <S extends T> void doSave(S entity) {

		var entityInformation = getEntityInformation(entity);

		if (entityInformation.isNew(entity)) {
			entityManager.persist(entity);
		} else {
			entityManager.merge(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.compositions.FlushOnSaveRepository#saveAll(java.lang.Iterable)
	 */
	@Transactional
	@Override
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {

		entities.forEach(this::doSave);

		entityManager.flush();

		return entities;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <S extends T> EntityInformation<Object, S> getEntityInformation(S entity) {

		var userClass = ClassUtils.getUserClass(entity.getClass());

		if (entity instanceof AbstractPersistable<?>) {
			return new JpaPersistableEntityInformation(userClass, entityManager.getMetamodel());
		}

		return new JpaMetamodelEntityInformation(userClass, entityManager.getMetamodel());
	}
}
