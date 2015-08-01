/*
 * Copyright 2014-2015 the original author or authors.
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
package example.springdata.jpa.security;

import static java.util.Collections.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test to show the usage of Spring Security constructs within Repository query methods.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SecurityConfiguration.class)
@Transactional
public class SecurityIntegrationTests {

	@Autowired UserRepository userRepository;
	@Autowired BusinessObjectRepository businessObjectRepository;
	@Autowired SecureBusinessObjectRepository secureBusinessObjectRepository;
	@Autowired EntityManager em;

	User tom, ollie, admin;
	UsernamePasswordAuthenticationToken olliAuth, tomAuth, adminAuth;
	BusinessObject object1, object2, object3;

	@Before
	public void setup() {

		tom = userRepository.save(new User("thomas", "darimont", "tdarimont@example.org"));
		ollie = userRepository.save(new User("oliver", "gierke", "ogierke@example.org"));
		admin = userRepository.save(new User("admin", "admin", "admin@example.org"));

		object1 = businessObjectRepository.save(new BusinessObject("object1", ollie));
		object2 = businessObjectRepository.save(new BusinessObject("object2", ollie));
		object3 = businessObjectRepository.save(new BusinessObject("object3", tom));

		olliAuth = new UsernamePasswordAuthenticationToken(ollie, "x");
		tomAuth = new UsernamePasswordAuthenticationToken(tom, "x");
		adminAuth = new UsernamePasswordAuthenticationToken(admin, "x",
				singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
	}

	@Test
	public void findBusinessObjectsForCurrentUserShouldReturnOnlyBusinessObjectsWhereCurrentUserIsOwner() {

		SecurityContextHolder.getContext().setAuthentication(tomAuth);

		List<BusinessObject> businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUser();

		assertThat(businessObjects, hasSize(1));
		assertThat(businessObjects, contains(object3));

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ollie, "x"));

		businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUser();

		assertThat(businessObjects, hasSize(2));
		assertThat(businessObjects, contains(object1, object2));
	}

	@Test
	public void findBusinessObjectsForCurrentUserShouldReturnAllObjectsForAdmin() {

		SecurityContextHolder.getContext().setAuthentication(adminAuth);

		List<BusinessObject> businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUser();

		assertThat(businessObjects, hasSize(3));
		assertThat(businessObjects, contains(object1, object2, object3));
	}

	@Test
	public void findBusinessObjectsForCurrentUserByIdShouldReturnOnlyBusinessObjectsWhereCurrentUserIsOwner() {

		SecurityContextHolder.getContext().setAuthentication(tomAuth);

		List<BusinessObject> businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUserById();

		assertThat(businessObjects, hasSize(1));
		assertThat(businessObjects, contains(object3));

		SecurityContextHolder.getContext().setAuthentication(olliAuth);

		businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUserById();

		assertThat(businessObjects, hasSize(2));
		assertThat(businessObjects, contains(object1, object2));
	}

	@Test
	public void findBusinessObjectsForCurrentUserByIdShouldReturnAllObjectsForAdmin() {

		SecurityContextHolder.getContext().setAuthentication(adminAuth);

		List<BusinessObject> businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUserById();

		assertThat(businessObjects, hasSize(3));
		assertThat(businessObjects, contains(object1, object2, object3));
	}

	@Test
	public void customUpdateStatementShouldAllowToUseSecurityContextInformationViaSpelParameters() {

		SecurityContextHolder.getContext().setAuthentication(adminAuth);

		// Detaching items to get them out of the query cache in order to see the updated values.
		em.detach(object1);
		em.detach(object2);
		em.detach(object3);

		secureBusinessObjectRepository.modifiyDataWithRecordingSecurityContext();

		for (BusinessObject bo : businessObjectRepository.findAll()) {

			assertThat(bo.getLastModifiedDate(), is(notNullValue()));
			assertThat(bo.getLastModifiedBy().getFirstname(), is("admin"));
		}
	}
}
