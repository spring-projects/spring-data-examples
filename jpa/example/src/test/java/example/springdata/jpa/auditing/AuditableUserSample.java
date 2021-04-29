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
package example.springdata.jpa.auditing;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Divya Srivastava
 * @author Jens Schauder
 */
@Transactional
@SpringBootTest
public class AuditableUserSample {

	@Autowired AuditableUserRepository repository;
	@Autowired AuditorAwareImpl auditorAware;
	@Autowired AuditingEntityListener listener;

	@Test
	void auditEntityCreation() {

		assertThat(ReflectionTestUtils.getField(listener, "handler")).isNotNull();

		var user = new AuditableUser();
		user.setUsername("username");

		auditorAware.setAuditor(user);

		user = repository.save(user);
		user = repository.save(user);

		assertThat(user.getCreatedBy()).isEqualTo(user);
		assertThat(user.getLastModifiedBy()).isEqualTo(user);
	}
}
