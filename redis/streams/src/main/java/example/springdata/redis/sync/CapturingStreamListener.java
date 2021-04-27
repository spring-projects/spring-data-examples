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
package example.springdata.redis.sync;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;

/**
 * @author Christoph Strobl
 * @author Mark Paluch
 */
public class CapturingStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

	private AtomicInteger counter = new AtomicInteger(0);
	private BlockingDeque<MapRecord<String, String, String>> deque = new LinkedBlockingDeque<>();

	private CapturingStreamListener() {
	}

	@Override
	public void onMessage(MapRecord<String, String, String> record) {

		counter.incrementAndGet();
		deque.add(record);
	}

	/**
	 * @return new instance of {@link CapturingStreamListener}.
	 */
	public static CapturingStreamListener create() {
		return new CapturingStreamListener();
	}

	/**
	 * @return the total number or records captured so far.
	 */
	public int recordsReceived() {
		return counter.get();
	}

	/**
	 * Retrieves and removes the head of the queue waiting if
	 * necessary until an element becomes available.
	 *
	 * @return
	 * @throws InterruptedException if interrupted while waiting
	 */
	public MapRecord<String, String, String> take() throws InterruptedException {
		return deque.take();
	}
}
