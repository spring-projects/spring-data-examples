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

import org.apache.geode.cache.TransactionEvent;
import org.apache.geode.cache.TransactionWriter;
import org.apache.geode.cache.TransactionWriterException;
import org.apache.geode.internal.cache.TXEntryState;
import org.apache.geode.internal.cache.TXEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomerTransactionWriter implements TransactionWriter {

	@Override
	public void beforeCommit(TransactionEvent transactionEvent) throws TransactionWriterException {
		AtomicBoolean six_found = new AtomicBoolean(false);
		((TXEvent) transactionEvent).getEvents().forEach(event -> {
			if (event instanceof TXEntryState.TxEntryEventImpl && ((TXEntryState.TxEntryEventImpl) event).getKey().equals(6L)) {
				six_found.set(true);
			}
		});

		if (six_found.get()) {
			throw new TransactionWriterException("Customer for Key: 6 is being changed. Failing transaction");
		}
	}
}