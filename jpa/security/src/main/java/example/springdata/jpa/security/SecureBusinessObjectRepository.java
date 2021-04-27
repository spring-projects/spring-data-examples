/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.jpa.security;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * @author Thomas Darimont
 * @auhtor Oliver Gierke
 */
interface SecureBusinessObjectRepository extends Repository<BusinessObject, Long> {

	/**
	 * Here we demonstrate the usage of SpEL expression within a custom query. With the
	 * {@link example.springdata.jpa.security.SecurityEvaluationContextExtension} in place we can safely access auth
	 * information provided by the Spring Security Context. The Spring Data Repository infrastructure will translate the
	 * given query string into the parameterized form: <code>
	 *     select o from BusinessObject o where o.owner.emailAddress like ?
	 * </code> and set the the result SpEL expression evaluated at method invocation time as parameter value.
	 *
	 * @return
	 */
	@Query("select o from BusinessObject o where o.owner.emailAddress like ?#{hasRole('ROLE_ADMIN') ? '%' : principal.emailAddress}")
	List<BusinessObject> findBusinessObjectsForCurrentUser();

	/**
	 * Here we apply a dynamic filter condition in there query depending of the role of the current principal.
	 *
	 * @return
	 */
	@Query("select o from BusinessObject o where o.owner.id = ?#{principal.id} or 1=?#{hasRole('ROLE_ADMIN') ? 1 : 0}")
	List<BusinessObject> findBusinessObjectsForCurrentUserById();

	/**
	 * Here we demonstrate the use of SecurityContext information in dynamic SpEL parameters in a JPQL update statement.
	 */
	@Modifying(clearAutomatically = true)
	@Query("update BusinessObject b set b.data = upper(b.data), b.lastModifiedBy = :#{#security.principal}, b.lastModifiedDate = :#{new java.util.Date()}")
	void modifiyDataWithRecordingSecurityContext();
}
