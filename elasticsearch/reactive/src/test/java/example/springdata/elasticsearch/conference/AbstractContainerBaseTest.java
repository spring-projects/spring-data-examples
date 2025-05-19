/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.elasticsearch.conference;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * singleton container
 *
 * @author Haibo Liu
 */
@SpringBootTest(classes = {ApplicationConfiguration.class, AbstractContainerBaseTest.TestConfiguration.class})
public class AbstractContainerBaseTest {

	private static final ElasticsearchContainer CONTAINER = new ElasticsearchContainer(
			DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.15.3")) //
			.withPassword("foobar");

	static {
		CONTAINER.start();
	}

	@Configuration
	static class TestConfiguration extends ElasticsearchConfiguration {
		@Override
		@NonNull
		public ClientConfiguration clientConfiguration() {

			Assert.notNull(CONTAINER, "TestContainer is not initialized!");

			return ClientConfiguration.builder() //
					.connectedTo(CONTAINER.getHttpHostAddress()) //
					.usingSsl(CONTAINER.createSslContextFromCa()) //
					.withBasicAuth("elastic", "foobar") //
					.build();
		}
	}
}
