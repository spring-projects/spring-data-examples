/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.server.eventhandlers;

import org.apache.geode.cache.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.gemfire.util.RegionUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = EventHandlerServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EventHandlerServerTest {
	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	@Resource(name = "Products")
	private Region<Long, Product> products;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ProductRepository productRepository;

	@Test
	public void customersRegionWasConfiguredCorrectly() {

		assertThat(this.customers).isNotNull();
		assertThat(this.customers.getName()).isEqualTo("Customers");
		assertThat(this.customers.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Customers"));
		assertThat(this.customers).isNotEmpty();
	}

	@Test
	public void productsRegionWasConfiguredCorrectly() {

		assertThat(this.products).isNotNull();
		assertThat(this.products.getName()).isEqualTo("Products");
		assertThat(this.products.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Products"));
		assertThat(this.products).isNotEmpty();
	}

	@Test
	public void customerRepositoryWasAutoConfiguredCorrectly() {

		assertThat(this.customerRepository.count()).isEqualTo(3);
	}

	@Test
	public void productRepositoryWasAutoConfiguredCorrectly() {

		assertThat(this.productRepository.count()).isEqualTo(4);
	}
}