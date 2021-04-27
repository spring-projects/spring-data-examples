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

package example.springdata.geode.server.wan.event.server;

import org.apache.geode.cache.wan.GatewayEventFilter;
import org.apache.geode.cache.wan.GatewayQueueEvent;
import org.springframework.stereotype.Component;

/**
 * @author Patrick Johnson
 */
@Component
public class EvenNumberedKeyWanEventFilter implements GatewayEventFilter {

	@Override
	public boolean beforeEnqueue(GatewayQueueEvent event) {
		return (Long) event.getKey() % 2 == 0;
	}

	@Override
	public boolean beforeTransmit(GatewayQueueEvent event) {
		return true;
	}

	@Override
	public void afterAcknowledgement(GatewayQueueEvent event) {}
}
