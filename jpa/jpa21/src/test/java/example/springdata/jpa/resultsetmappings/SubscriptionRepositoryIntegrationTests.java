/*
 * Copyright 2018-2021 the original author or authors.
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
package example.springdata.jpa.resultsetmappings;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Thomas Darimont
 * @author Divya Srivastava
 */
@DataJpaTest
@Transactional
class SubscriptionRepositoryIntegrationTests {

	private static final String SERVICE_1 = "Service 1";
	private static final String SERVICE_2 = "Service 2";

	@Autowired SubscriptionRepository repository;

	@BeforeEach
	void setUp() {

		repository.save(new Subscription(SERVICE_1, 1));
		repository.save(new Subscription(SERVICE_1, 2));
		repository.save(new Subscription(SERVICE_1, 3));
		repository.save(new Subscription(SERVICE_2, 3));
		repository.save(new Subscription(SERVICE_2, 4));
	}

	@Test
	void shouldReturnCorrectSubscriptionSummary() {

		assertThat(repository.findAllSubscriptionSummaries()) //
				.flatExtracting(s -> asList(s.product(), s.usageCount())) //
				.contains(SERVICE_1, 3L, SERVICE_2, 2L);
	}

	@Test
	void shouldReturnCorrectSubscriptionProjection() {

		assertThat(repository.findAllSubscriptionProjections()) //
				.flatExtracting(s -> asList(s.getProduct(), s.getUsageCount())) //
				.contains(SERVICE_1, 3L, SERVICE_2, 2L);
	}
}
