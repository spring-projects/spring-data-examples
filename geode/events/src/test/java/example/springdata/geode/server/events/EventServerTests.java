/*
 * Copyright 2020 the original author or authors.
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

package example.springdata.geode.server.events;

import org.apache.geode.cache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = EventServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EventServerTests {

	@Autowired
	Cache cache;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderProductSummaryRepository orderProductSummaryRepository;

	@Test
	public void asyncEventQueueEasConfiguredCorrectly() {
		assertThat(this.orderProductSummaryRepository.count()).isEqualTo(3);
	}

	@Test
	public void productCacheLoaderWorks() {
		long size = productRepository.count();
		assertThat(this.productRepository.findById(777L)).isNotNull();
		assertThat(productRepository.count()).isEqualTo(size + 1);
		productRepository.deleteById(777L);
	}
}