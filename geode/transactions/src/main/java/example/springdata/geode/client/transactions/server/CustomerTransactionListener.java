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
package example.springdata.geode.client.transactions.server;

import java.util.stream.Collectors;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import org.apache.geode.cache.CacheEvent;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.TransactionEvent;
import org.apache.geode.cache.util.TransactionListenerAdapter;

/**
 * @author Patrick Johnson
 */
@Component
@CommonsLog
public class CustomerTransactionListener extends TransactionListenerAdapter {

	@Override
	public void afterFailedCommit(TransactionEvent event) {

	}

	@Override
	public void afterRollback(TransactionEvent event) {
		log.info("In afterRollback for entry(s) ["
				+ event.getEvents().stream().map(this::getEventInfo).collect(Collectors.toList()) + "]");
	}

	private String getEventInfo(CacheEvent<?, ?> cacheEvent) {
		if (cacheEvent instanceof EntryEvent) {
			return ((EntryEvent<?, ?>) cacheEvent).getNewValue().toString();
		} else {
			return cacheEvent.toString();
		}
	}
}
