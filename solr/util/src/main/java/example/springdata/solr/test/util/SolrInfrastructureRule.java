/*
 * Copyright 2018 the original author or authors.
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
package example.springdata.solr.test.util;

import example.springdata.test.util.InfrastructureRule;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.core.Is;
import org.junit.Assume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;

/**
 * A JUnit Rule to locate or create a Solr instance including one of the sets of example data that is part of the Solr distribution.
 *
 * @author Jens Schauder
 */
public class SolrInfrastructureRule extends InfrastructureRule<String> {

	private static final Logger LOG = LoggerFactory.getLogger(SolrInfrastructureRule.class);

	public SolrInfrastructureRule(String example) {
		super(() -> checkNativeInstance("http://localhost:8983/solr", "/admin/info/system"),
				() -> tryToStartInstanceInDocker(example));

	}

	private static InfrastructureInfo<String> checkNativeInstance(String baseUrl, String path) {

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			CloseableHttpResponse response = client.execute(new HttpGet(baseUrl + path));
			if (response != null && response.getStatusLine() != null) {
				Assume.assumeThat(response.getStatusLine().getStatusCode(), Is.is(200));
			}

			return new InfrastructureInfo<>(true, baseUrl, null, () -> {});
		} catch (IOException e) {
			return new InfrastructureInfo<>(false, null, e, () -> {});
		}
	}

	private static InfrastructureInfo tryToStartInstanceInDocker(String example) {

		SolrExampleContainer solrContainer = //
				new SolrExampleContainer(example) //
						.withLogConsumer(new Slf4jLogConsumer(LOG));

		solrContainer.start();

		return new InfrastructureInfo<>(true, solrContainer.getSolrBaseUrl(), null, () -> solrContainer.stop());
	}

}
