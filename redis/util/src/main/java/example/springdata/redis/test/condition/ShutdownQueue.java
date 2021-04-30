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

import java.io.Closeable;
import java.util.LinkedList;

/**
 * Shutdown queue allowing ordered resource shutdown (LIFO).
 *
 * @author Mark Paluch
 */
enum ShutdownQueue {

	INSTANCE;

	static {

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {

				Closeable closeable;
				while ((closeable = INSTANCE.closeables.pollLast()) != null) {
					try {
						closeable.close();
					} catch (Exception o_O) {
						// ignore
						o_O.printStackTrace();
					}
				}

			}
		});
	}

	private final LinkedList<Closeable> closeables = new LinkedList<>();

	public static void register(Closeable closeable) {
		INSTANCE.closeables.add(closeable);
	}
}
