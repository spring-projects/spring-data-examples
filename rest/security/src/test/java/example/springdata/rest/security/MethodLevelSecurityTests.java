/*
 * Copyright 2014-2018 the original author or authors.
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
package example.springdata.rest.security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Collection of test cases used to verify method-level security.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MethodLevelSecurityTests {

	@Autowired ItemRepository itemRepository;

	@Before
	public void setUp() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void rejectsMethodInvocationsForNoAuth() {

		try {
			itemRepository.findAll();
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}

		try {
			itemRepository.save(new Item("MacBook Pro"));
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}

		try {
			itemRepository.deleteById(1L);
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}
	}

	@Test
	public void rejectsMethodInvocationsForAuthWithInsufficientPermissions() {

		SecurityUtils.runAs("system", "system", "ROLE_USER");

		itemRepository.findAll();

		try {
			itemRepository.save(new Item("MacBook Pro"));
			fail("Expected a security error");
		} catch (AccessDeniedException e) {
			// expected
		}
		try {
			itemRepository.deleteById(1L);
			fail("Expected a security error");
		} catch (AccessDeniedException e) {
			// expected
		}
	}

	@Test
	public void allowsMethodInvocationsForAuthWithSufficientPermissions() {

		SecurityUtils.runAs("system", "system", "ROLE_USER", "ROLE_ADMIN");

		itemRepository.findAll();

		Item item = itemRepository.save(new Item("MacBook Pro"));

		itemRepository.deleteById(item.getId());
	}
}
