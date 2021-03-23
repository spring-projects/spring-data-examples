/*
 * Copyright 2019-2021 the original author or authors.
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

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.io.IOException;
import java.util.Optional;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author Christoph Strobl
 * @author Prakhar Gupta
 */
public class ElasticsearchAvailable implements ExecutionCondition {

	private boolean checkServerRunning(String url) {
		boolean isConnectionAvailable = false;

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			CloseableHttpResponse response = client.execute(new HttpHead(url));

			if (response != null && response.getStatusLine() != null) {
				isConnectionAvailable = true;
			}
		} catch (IOException e) {
			return isConnectionAvailable;
		}

		return isConnectionAvailable;
	}

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext extensionContext) {
		Optional<AssumeConnection> annotation = findAnnotation(extensionContext.getElement(), AssumeConnection.class);

		if (annotation.isPresent()) {
			String url = annotation.get().url();

			if (checkServerRunning(url)) {
				return ConditionEvaluationResult
						.enabled("Successfully connected to Elasticsearch server. Continuing test!");
			} else {
				return ConditionEvaluationResult.disabled("Elasticsearch Server seems to be down. Skipping test!");
			}
		}

		return ConditionEvaluationResult.disabled("No connection specified. Skipping test!");
	}
}
