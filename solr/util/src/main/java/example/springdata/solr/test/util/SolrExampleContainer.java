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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testcontainers.containers.GenericContainer;

/**
 * @author Jens Schauder
 */
public class SolrExampleContainer extends GenericContainer<SolrExampleContainer> {

	private static final String IMAGE_NAME = "solr";
	private static final String DEFAULT_VERSION = "7";
	private static final int DEFAULT_PORT = 8983;
	private final String example;

	public SolrExampleContainer(final String example) {

		super(IMAGE_NAME + ":" + DEFAULT_VERSION);
		this.example = example;
		this.withExposedPorts(DEFAULT_PORT) //
				.withCommand("bash", "-c", "solr start -e " + example + " && tail -f /dev/null");

	}

	@Override
	public void start() {
		super.start();

		Integer port = getMappedPort(DEFAULT_PORT);
		String url = "http://localhost:" + port + "/" + IMAGE_NAME;

		waitForSolr(url);

	}

	private void waitForSolr(String url) {

		long waitStart = System.currentTimeMillis();
		long maxWaitDuration = 10000;

		do {

			try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

				CloseableHttpResponse response = client.execute(new HttpGet(url + "/" + example + "/admin/ping"));
				if (response != null && response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200) {

					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (System.currentTimeMillis() < waitStart + maxWaitDuration);
	}

	public String getSolrBaseUrl() {
		return  "http://localhost:" + getMappedPort(8983) + "/solr";
	}
}
