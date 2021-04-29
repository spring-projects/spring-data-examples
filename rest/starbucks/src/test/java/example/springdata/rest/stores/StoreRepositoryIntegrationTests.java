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
package example.springdata.rest.stores;

import static org.assertj.core.api.Assertions.*;

import example.springdata.rest.stores.Store.Address;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

/**
 * Integration tests for {@link StoreRepository}.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@SpringBootTest(classes = Application.class)
public class StoreRepositoryIntegrationTests {

	@Autowired StoreRepository repository;

	@BeforeEach
	@AfterEach
	public void clearDb() {
		repository.deleteAll();
	}

	@Test
	public void findsStoresByLocation() {

		var location = new Point(-73.995146, 40.740337);
		var store = new Store(UUID.randomUUID(), "Foo", new Address("street", "city", "zip", location));

		store = repository.save(store);

		var stores = repository.findByAddressLocationNear(location, new Distance(1.0, Metrics.KILOMETERS),
				PageRequest.of(0, 10));

		assertThat(stores.getContent()).hasSize(1);
		assertThat(stores.getContent()).contains(store);
	}
}
