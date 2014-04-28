/*
 * Copyright 2014 the original author or authors.
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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Intergration test showing the usage of JPA 2.1 stored procedures support through Spring Data repositories.
 * 
 * @author Thomas Darimont
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringApplicationConfiguration(classes = StoredProcedureConfiguration.class)
public class UserRepositoryIntegrationTests {

	@Autowired UserRepository repository;

	/**
	 * @see DATAJPA-455
	 */
	@Test
	public void entityAnnotatedCustomNamedProcedurePlus1IO() {
		assertThat(repository.plus1BackedByOtherNamedStoredProcedure(1), is(2));
	}

	/**
	 * @see DATAJPA-455
	 */
	@Test
	public void invokeDerivedStoredProcedure() {
		assertThat(repository.plus1inout(1), is(2));
	}

	// This is what it would look like implemented manually.

	@Autowired EntityManager em;

	@Test
	public void plainJpa21() {

		StoredProcedureQuery proc = em.createStoredProcedureQuery("plus1inout");
		proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

		proc.setParameter(1, 1);
		proc.execute();

		assertThat(proc.getOutputParameterValue(2), is((Object) 2));
	}

	@Test
	public void plainJpa21_entityAnnotatedCustomNamedProcedurePlus1IO() {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("User.plus1");

		proc.setParameter("arg", 1);
		proc.execute();

		assertThat(proc.getOutputParameterValue("res"), is((Object) 2));
	}
}
