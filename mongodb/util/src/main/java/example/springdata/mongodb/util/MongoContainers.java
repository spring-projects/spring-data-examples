/*
 * Copyright 2021 the original author or authors.
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

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Utility methods to create a {@link MongoDBContainer}.
 *
 * @author Mark Paluch
 */
public class MongoContainers {

	private static final String IMAGE_NAME = "mongo:5.0";
	private static final String IMAGE_NAME_PROPERTY = "mongo.default.image.name";

	public static MongoDBContainer getDefaultContainer() {
		return new MongoDBContainer(DockerImageName.parse(System.getProperty(IMAGE_NAME_PROPERTY, IMAGE_NAME)))
				.withReuse(true);
	}
}
