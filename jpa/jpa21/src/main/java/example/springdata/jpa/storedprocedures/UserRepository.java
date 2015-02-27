/*
 * Copyright 2013-2015 the original author or authors.
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
package example.springdata.jpa.storedprocedures;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

/**
 * Simple repository interface for {@link User} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Explicitly mapped to named stored procedure {@code User.plus1IO} in the {@link EntityManager}
	 * 
	 * @see User
	 */
	@Procedure(name = "User.plus1")
	Integer plus1BackedByOtherNamedStoredProcedure(Integer arg);

	/**
	 * Directly map the method to the stored procedure in the database (to avoid the annotation madness on your domain
	 * classes).
	 */
	@Procedure
	Integer plus1inout(Integer arg);
}
