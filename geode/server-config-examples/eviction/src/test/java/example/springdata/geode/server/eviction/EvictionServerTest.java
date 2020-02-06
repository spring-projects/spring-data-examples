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

package example.springdata.geode.server.eviction;

import org.apache.geode.cache.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = EvictionServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EvictionServerTest {

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	@Resource(name = "Products")
	private Region<Long, Product> products;

	@Resource(name = "Orders")
	private Region<Long, Order> orders;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void clearMemory() {
		System.gc();
	}

	@Test
	public void customerRepositoryWasAutoConfiguredCorrectly() {
		assertThat(this.customers.size()).isEqualTo(300);
	}

	@Test
	public void productRepositoryWasAutoConfiguredCorrectly() {
		assertThat(this.products.size()).isEqualTo(300);
	}

	@Test
	public void orderRepositoryWasAutoConfiguredCorrectly() {
		long numOrders = this.orders.size();
		logger.info("There are " + numOrders + " orders in the Orders region");
		assertThat(numOrders).isEqualTo(10);
	}

	@Test
	public void evictionIsConfiguredCorrectly() {
		assertThat(customers.getAttributes().getEvictionAttributes().getAction().isOverflowToDisk()).isTrue();
		assertThat(products.getAttributes().getEvictionAttributes().getAction().isOverflowToDisk()).isTrue();
		assertThat(orders.getAttributes().getEvictionAttributes().getAction().isLocalDestroy()).isTrue();
	}
}
