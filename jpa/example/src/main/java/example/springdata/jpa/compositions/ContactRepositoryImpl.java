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

import java.util.List;

import javax.persistence.EntityManager;

/**
 * {@link ContactRepository} fragment implementation.
 *
 * @author Mark Paluch
 */
@RequiredArgsConstructor
public class ContactRepositoryImpl implements ContactRepository {

	final @NonNull EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.compositions.ContactRepository#findRelatives(example.springdata.jpa.compositions.Contact)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Contact> findRelatives(Contact contact) {

		return entityManager.createQuery("SELECT u FROM User u WHERE u.lastname = :lastname") //
				.setParameter("lastname", contact.getLastname()) //
				.getResultList();
	}
}
