/*
 * Copyright 2018 the original author or authors.
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
package example.springdata.jpa.eclipselink;

import static org.assertj.core.api.Assertions.*;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Oliver Gierke
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class Issue349477IntegrationTest {

	@Autowired EntityManager em;

	@Test
	public void testname() {

		MyUser user = new MyUser("Dave", "Matthews", "foo@bar.de");
		em.persist(user);
		em.flush();

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<MyUser> criteria = builder.createQuery(MyUser.class);
		Root<MyUser> root = criteria.from(MyUser.class);
		criteria.where(root.get("firstname").in(builder.parameter(Collection.class)));

		TypedQuery<MyUser> query = em.createQuery(criteria);

		for (ParameterExpression parameter : criteria.getParameters()) {
			query.setParameter(parameter, Arrays.asList("Dave", "Carter"));
		}

		List<MyUser> result = query.getResultList();
		assertThat(result).isEmpty();
	}

	@Data
	@RequiredArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
	@Entity
	@Table(name = "Foo")
	static class MyUser {
		private @GeneratedValue @Id Long id;
		private final String firstname, lastname, emailAddress;
	}
}
