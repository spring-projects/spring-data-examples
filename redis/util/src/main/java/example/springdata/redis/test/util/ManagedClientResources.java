/*
 * Copyright 2017-2018 the original author or authors.
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
package example.springdata.redis.test.util;

import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility to keep track of a single {@link ClientResources} instance used from test rules to prevent costly
 * creation/disposal of threading resources.
 *
 * @author Mark Paluch
 */
class ManagedClientResources {

	private static final ManagedClientResources instance = new ManagedClientResources();

	private final AtomicReference<ClientResources> clientResources = new AtomicReference<>();

	/**
	 * Obtain a managed instance of {@link ClientResources}. Allocates an instance if {@link ManagedClientResources} was
	 * not initialized already.
	 *
	 * @return the {@link ClientResources}.
	 */
	static ClientResources getClientResources() {

		AtomicReference<ClientResources> ref = instance.clientResources;

		ClientResources clientResources = ref.get();
		if (clientResources != null) {
			return clientResources;
		}

		clientResources = DefaultClientResources.create();

		if (ref.compareAndSet(null, clientResources)) {
			return clientResources;
		}

		clientResources.shutdown(0, 0, TimeUnit.SECONDS);

		return ref.get();
	}

}
