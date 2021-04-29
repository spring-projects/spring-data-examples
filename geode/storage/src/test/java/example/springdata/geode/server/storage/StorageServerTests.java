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
package example.springdata.geode.server.storage;

import static org.assertj.core.api.Assertions.*;

import javax.annotation.Resource;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.Region;
import org.apache.geode.compression.SnappyCompressor;
import org.apache.geode.internal.cache.GemFireCacheImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Patrick Johnson
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServerTests {

	@Resource(name = "Customers") private Region<Long, Customer> customers;

	@Resource(name = "Orders") private Region<Long, Order> orders;

	@Resource(name = "Products") private Region<Long, Product> products;

	@Autowired Cache cache;

	@Test
	public void partitionAttributesConfiguredCorrectly() {
		assertThat(this.orders.getAttributes().getPartitionAttributes().getTotalNumBuckets()).isEqualTo(11);
		assertThat(this.orders.getAttributes().getPartitionAttributes().getRedundantCopies()).isEqualTo(1);
	}

	@Test
	public void compressorIsEnabled() {

		assertThat(customers.getAttributes().getCompressor()).isInstanceOf(SnappyCompressor.class);

		var impl = (GemFireCacheImpl) cache;

		assertThat(impl.getCachePerfStats().getTotalPostCompressedBytes())
				.isLessThan(impl.getCachePerfStats().getTotalPreCompressedBytes());
	}

	@Test
	public void offHeapConfiguredCorrectly() {
		assertThat(products.getAttributes().getOffHeap()).isTrue();
		assertThat(customers.getAttributes().getOffHeap()).isFalse();
	}
}
