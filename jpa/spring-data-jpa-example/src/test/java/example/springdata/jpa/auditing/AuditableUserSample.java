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
package example.springdata.jpa.auditing;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import example.springdata.jpa.auditing.AuditableUser;
import example.springdata.jpa.auditing.AuditableUserRepository;
import example.springdata.jpa.auditing.AuditingConfiguration;
import example.springdata.jpa.auditing.AuditorAwareImpl;

/**
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = AuditingConfiguration.class)
public class AuditableUserSample {

	@Autowired AuditableUserRepository repository;
	@Autowired AuditorAwareImpl auditorAware;
	@Autowired AuditingEntityListener listener;

	@Test
	public void auditEntityCreation() throws Exception {

		assertThat(ReflectionTestUtils.getField(listener, "handler"), is(notNullValue()));

		AuditableUser user = new AuditableUser();
		user.setUsername("username");

		auditorAware.setAuditor(user);

		user = repository.save(user);
		user = repository.save(user);

		assertEquals(user, user.getCreatedBy());
		assertEquals(user, user.getLastModifiedBy());
	}
}
