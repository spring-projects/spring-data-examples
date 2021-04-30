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
package example.springdata.redis.test.condition;

import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

import java.util.concurrent.TimeUnit;

/**
 * Client-Resources suitable for testing. Every time a new {@link LettuceTestClientResources} instance is created, a
 * {@link Runtime#addShutdownHook(Thread) shutdown hook} is added to close the client resources.
 *
 * @author Mark Paluch
 * @author Christoph Strobl
 */
class LettuceTestClientResources {

	private static final ClientResources SHARED_CLIENT_RESOURCES;

	static {

		SHARED_CLIENT_RESOURCES = DefaultClientResources.builder().build();
		ShutdownQueue.INSTANCE.register(() -> SHARED_CLIENT_RESOURCES.shutdown(0, 0, TimeUnit.MILLISECONDS));
	}

	private LettuceTestClientResources() {}

	/**
	 * @return the client resources.
	 */
	public static ClientResources getSharedClientResources() {
		return SHARED_CLIENT_RESOURCES;
	}
}
