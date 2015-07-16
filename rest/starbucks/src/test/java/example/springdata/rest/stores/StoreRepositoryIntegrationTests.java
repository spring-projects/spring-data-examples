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
package example.springdata.rest.stores;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import example.springdata.rest.stores.Address;
import example.springdata.rest.stores.Store;
import example.springdata.rest.stores.StoreApp;
import example.springdata.rest.stores.StoreRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Integration tests for {@link StoreRepository}.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = StoreApp.class)
public class StoreRepositoryIntegrationTests {

	@Autowired StoreRepository repository;

	@Before
	@After
	public void clearDb() {
		repository.deleteAll();
	}

	@Test
	public void findsStoresByLocation() {

		Point location = new Point(-73.995146, 40.740337);
		Store store = new Store("Foo", new Address("street", "city", "zip", location));

		store = repository.save(store);

		Page<Store> stores = repository.findByAddressLocationNear(location, new Distance(1.0, Metrics.KILOMETERS),
				new PageRequest(0, 10));

		assertThat(stores.getContent(), hasSize(1));
		assertThat(stores.getContent(), hasItem(store));
	}
}
