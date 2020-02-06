/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.client.transactions.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.geode.cache.CacheEvent;
import org.apache.geode.cache.TransactionEvent;
import org.apache.geode.cache.util.TransactionListenerAdapter;
import org.apache.geode.internal.cache.TXEntryState;

import java.util.stream.Collectors;

public class CustomerTransactionListener extends TransactionListenerAdapter {
	private Log log = LogFactory.getLog(CustomerTransactionListener.class);

	@Override
	public void afterFailedCommit(TransactionEvent event) {

	}

	@Override
	public void afterRollback(TransactionEvent event) {
		log.info("In afterRollback for entry(s) [" + event.getEvents().stream().map(this::getEventInfo).collect(Collectors.toList()) + "]");
	}

	private String getEventInfo(CacheEvent cacheEvent) {
		if (cacheEvent instanceof TXEntryState.TxEntryEventImpl) {
			return ((TXEntryState.TxEntryEventImpl) cacheEvent).getNewValue().toString();
		} else {
			return cacheEvent.toString();
		}
	}
}