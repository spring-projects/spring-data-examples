/*
 * Copyright 2022 the original author or authors.
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

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of custom logic for a minion repository.
 *
 * @author Jens Schauder
 */
class PartyHatRepositoryImpl implements PartyHatRepository {


	private final NamedParameterJdbcOperations template;

	public PartyHatRepositoryImpl(NamedParameterJdbcOperations template) {
		this.template = template;
	}

	@Override
	public void addPartyHat(Minion minion) {

		Map<String, Object> insertParams = new HashMap<>();
		insertParams.put("id", minion.id);
		insertParams.put("name", "Party Hat");
		template.update("INSERT INTO TOY (MINION, NAME) VALUES (:id, :name)", insertParams);

		Map<String, Object> updateParams = new HashMap<>();
		updateParams.put("id", minion.id);
		updateParams.put("version", minion.version);
		final int updateCount = template.update("UPDATE MINION SET VERSION = :version + 1 WHERE ID = :id AND VERSION = :version", updateParams);
		if (updateCount != 1) {
			throw new OptimisticLockingFailureException("Minion was changed before a Party Hat was given");
		}
	}
}
