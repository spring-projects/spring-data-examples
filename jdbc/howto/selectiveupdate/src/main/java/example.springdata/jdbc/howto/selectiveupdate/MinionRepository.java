/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.jdbc.howto.selectiveupdate;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;


/**
 * The normal MinionRepository.
 *
 * @author Jens Schauder
 */
interface MinionRepository extends CrudRepository<Minion, Long>, PartyHatRepository {

	@Modifying
	@Query("UPDATE MINION SET COLOR ='PURPLE', VERSION = VERSION +1 WHERE ID = :id")
	void turnPurple(Long id);

}
