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

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test to show the usage of Spring Security constructs within Repository query methods.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Divya Srivastava
 * @author Jens Schauder
 */
@SpringBootTest
@Transactional
class SecurityIntegrationTests {

	@Autowired UserRepository userRepository;
	@Autowired BusinessObjectRepository businessObjectRepository;
	@Autowired SecureBusinessObjectRepository secureBusinessObjectRepository;

	private User tom, ollie, admin;
	private UsernamePasswordAuthenticationToken olliAuth, tomAuth, adminAuth;
	private BusinessObject object1, object2, object3;

	@BeforeEach
	void setup() {

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
	void findBusinessObjectsForCurrentUserShouldReturnOnlyBusinessObjectsWhereCurrentUserIsOwner() {

		SecurityContextHolder.getContext().setAuthentication(tomAuth);

		var businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUser();

		assertThat(businessObjects).containsExactly(object3);

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ollie, "x"));

		businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUser();

		assertThat(businessObjects).containsExactly(object1, object2);
	}

	@Test
	void findBusinessObjectsForCurrentUserShouldReturnAllObjectsForAdmin() {

		SecurityContextHolder.getContext().setAuthentication(adminAuth);

		var businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUser();

		assertThat(businessObjects).containsExactly(object1, object2, object3);
	}

	@Test
	void findBusinessObjectsForCurrentUserByIdShouldReturnOnlyBusinessObjectsWhereCurrentUserIsOwner() {

		SecurityContextHolder.getContext().setAuthentication(tomAuth);

		var businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUserById();

		assertThat(businessObjects).containsExactly(object3);

		SecurityContextHolder.getContext().setAuthentication(olliAuth);

		businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUserById();

		assertThat(businessObjects).containsExactly(object1, object2);
	}

	@Test
	void findBusinessObjectsForCurrentUserByIdShouldReturnAllObjectsForAdmin() {

		SecurityContextHolder.getContext().setAuthentication(adminAuth);

		var businessObjects = secureBusinessObjectRepository.findBusinessObjectsForCurrentUserById();

		assertThat(businessObjects).containsExactly(object1, object2, object3);
	}

	@Test
	void customUpdateStatementShouldAllowToUseSecurityContextInformationViaSpelParameters() {

		SecurityContextHolder.getContext().setAuthentication(adminAuth);

		secureBusinessObjectRepository.modifiyDataWithRecordingSecurityContext();

		for (var bo : businessObjectRepository.findAll()) {

			assertThat(bo.getLastModifiedDate()).isNotNull();
			assertThat(bo.getLastModifiedBy().getFirstname()).isEqualTo("admin");
		}
	}
}
