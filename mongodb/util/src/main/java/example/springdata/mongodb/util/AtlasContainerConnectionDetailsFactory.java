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
package example.springdata.mongodb.util;

import org.springframework.boot.autoconfigure.mongo.MongoConnectionDetails;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionDetailsFactory;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionSource;

import com.mongodb.ConnectionString;

public class AtlasContainerConnectionDetailsFactory
		extends ContainerConnectionDetailsFactory<AtlasContainer, MongoConnectionDetails> {

	AtlasContainerConnectionDetailsFactory() {
		super(ANY_CONNECTION_NAME, new String[] { "com.mongodb.ConnectionString" });
	}

	protected MongoConnectionDetails getContainerConnectionDetails(ContainerConnectionSource<AtlasContainer> source) {
		return new MongoContainerConnectionDetails(source);
	}

	private static final class MongoContainerConnectionDetails extends
			ContainerConnectionDetailsFactory.ContainerConnectionDetails<AtlasContainer> implements MongoConnectionDetails {

		private MongoContainerConnectionDetails(ContainerConnectionSource<AtlasContainer> source) {
			super(source);
		}

		public ConnectionString getConnectionString() {
			return new ConnectionString(this.getContainer().getConnectionString());
		}

		public SslBundle getSslBundle() {
			return super.getSslBundle();
		}
	}
}
