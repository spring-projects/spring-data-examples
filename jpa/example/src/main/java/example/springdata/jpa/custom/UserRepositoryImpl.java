/*
 * Copyright 2013-2021 the original author or authors.
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
package example.springdata.jpa.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation fo the custom repository functionality declared in {@link UserRepositoryCustom} based on JPA. To use
 * this implementation in combination with Spring Data JPA you can either register it programatically:
 *
 * <pre>
 * EntityManager em = ... // Obtain EntityManager
 *
 * UserRepositoryCustom custom = new UserRepositoryImpl();
 * custom.setEntityManager(em);
 *
 * RepositoryFactorySupport factory = new JpaRepositoryFactory(em);
 * UserRepository repository = factory.getRepository(UserRepository.class, custom);
 * </pre>
 *
 * Using the Spring namespace the implementation will just get picked up due to the classpath scanning for
 * implementations with the {@code Impl} postfix.
 *
 * <pre>
 * &lt;jpa:repositories base-package=&quot;com.acme.repository&quot; /&gt;
 * </pre>
 *
 * If you need to manually configure the custom instance see {@link UserRepositoryImplJdbc} for an example.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext private EntityManager em;

	/**
	 * Configure the entity manager to be used.
	 *
	 * @param em the {@link EntityManager} to set.
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.UserRepositoryCustom#myCustomBatchOperation()
	 */
	public List<User> myCustomBatchOperation() {

		var criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
		criteriaQuery.select(criteriaQuery.from(User.class));
		return em.createQuery(criteriaQuery).getResultList();
	}
}
