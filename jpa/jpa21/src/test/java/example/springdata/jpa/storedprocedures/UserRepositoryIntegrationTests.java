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
package example.springdata.jpa.storedprocedures;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the usage of JPA 2.1 stored procedures support through Spring Data repositories.
 *
 * @author Thomas Darimont
 * @author Oliver Gierke
 * @author Divya Srivastava
 * @author Jens Schauder
 */
@SpringBootTest
@Transactional
class UserRepositoryIntegrationTests {

	@Autowired UserRepository repository;
	// This is what it would look like implemented manually.

	@Autowired EntityManager em;

	/**
	 * @see DATAJPA-455
	 */
	@Test
	void entityAnnotatedCustomNamedProcedurePlus1IO() {
		assertThat(repository.plus1BackedByOtherNamedStoredProcedure(1)).isEqualTo(2);
	}

	/**
	 * @see DATAJPA-455
	 */
	@Test
	void invokeDerivedStoredProcedure() {
		assertThat(repository.plus1inout(1)).isEqualTo(2);
	}


	@Test
	void plainJpa21() {

		var proc = em.createStoredProcedureQuery("plus1inout");
		proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

		proc.setParameter(1, 1);
		proc.execute();

		assertThat(proc.getOutputParameterValue(2)).isEqualTo(2);
	}

	@Test
	void plainJpa21_entityAnnotatedCustomNamedProcedurePlus1IO() {

		var proc = em.createNamedStoredProcedureQuery("User.plus1");

		proc.setParameter("arg", 1);
		proc.execute();

		assertThat(proc.getOutputParameterValue("res")).isEqualTo(2);
	}
}
