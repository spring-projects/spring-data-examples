/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.geode.server.expiration.eviction;

import static org.assertj.core.api.Assertions.*;

import lombok.extern.apachecommons.CommonsLog;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;

/**
 * @author Patrick Johnson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@CommonsLog
public class EntityDefinedExpirationTests {

	@Autowired private OrderRepository orderRepository;

	@Autowired Faker faker;

	@Test
	public void entityDefinedExpirationIsConfiguredCorrectly() {

		orderRepository.save(new Order(1L, 50L,
				new Address(faker.address().streetAddress(), faker.address().city(), faker.address().country())));

		assertThat(orderRepository.count()).isEqualTo(1);

		log.info("Starting TTL wait period: " + Instant.now());
		// Due to the constant "getting" of the entry, the idle expiry timeout will not be met and the time-to-live
		// will be used.
		Awaitility.await().pollInterval(1, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS)
				.until(() -> !orderRepository.findById(1L).isPresent());

		assertThat(orderRepository.count()).isEqualTo(0);

		log.info("Ending TTL wait period: " + Instant.now());

		orderRepository.save(new Order(1L, 50L,
				new Address(faker.address().streetAddress(), faker.address().city(), faker.address().country())));

		assertThat(orderRepository.count()).isEqualTo(1);

		log.info("Starting Idle wait period: " + Instant.now());

		// Due to the delay in "getting" the entry, the idle timeout of 2s should delete the entry.
		Awaitility.await().pollDelay(2, TimeUnit.SECONDS).pollInterval(100, TimeUnit.MILLISECONDS)
				.atMost(10, TimeUnit.SECONDS).until(() -> !orderRepository.findById(1L).isPresent());

		assertThat(orderRepository.count()).isEqualTo(0);

		log.info("Ending Idle wait period: " + Instant.now());
	}
}
