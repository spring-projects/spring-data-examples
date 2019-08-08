/*
 * Copyright 2019 the original author or authors.
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
package example.springdata.elasticsearch.util;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assumptions;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Christoph Strobl
 */
public class ElasticsearchAvailable implements TestRule {

	private final String url;

	private ElasticsearchAvailable(String url) {
		this.url = url;
	}

	public static ElasticsearchAvailable onLocalhost() {
		return new ElasticsearchAvailable("http://localhost:9200");
	}

	@Override
	public Statement apply(Statement base, Description description) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {

				checkServerRunning();
				base.evaluate();
			}
		};
	}

	private void checkServerRunning() {

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			CloseableHttpResponse response = client.execute(new HttpHead(url));
			if (response != null && response.getStatusLine() != null) {
				Assumptions.assumeThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
			}
		} catch (IOException e) {
			throw new AssumptionViolatedException(String.format("Elasticsearch Server seems to be down. %s", e.getMessage()));
		}
	}
}
