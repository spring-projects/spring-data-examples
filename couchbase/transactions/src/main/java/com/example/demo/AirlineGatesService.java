/*
 * Copyright 2022 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo;

import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.ReactiveCouchbaseOperations;
import org.springframework.data.couchbase.core.ReactiveCouchbaseTemplate;
import org.springframework.data.couchbase.core.TransactionalSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author Michael Reiche
 */
@Service
public class AirlineGatesService {
	CouchbaseTemplate template;
	ReactiveCouchbaseTemplate reactiveTemplate;
	public AirlineGatesService(CouchbaseTemplate template) {
		this.template = template;
		this.reactiveTemplate = template.reactive();
	}


	// The @Transactional annotation results in the method of the proxy for the service executing this in a transaction
	@Transactional
	public void transferGates(String fromId, String toId, int gatesToTransfer, RuntimeException exceptionToThrow) {
		// May wish to include this check to confirm this is actually in a transaction.
		TransactionalSupport.checkForTransactionInThreadLocalStorage().map((h) -> {
			if (!h.isPresent())
				throw new RuntimeException("not in transaction!");
			return h;
		});

		AirlineGates fromAirlineGates = template.findById(AirlineGates.class).one(fromId);
		AirlineGates toAirlineGates = template.findById(AirlineGates.class).one(toId);
		toAirlineGates.gates += gatesToTransfer;
		fromAirlineGates.gates -= gatesToTransfer;
		template.save(fromAirlineGates);
		if(exceptionToThrow != null){
			throw exceptionToThrow;
		}
		template.save(toAirlineGates);
	}
	// The @Transactional annotation results in the method of the proxy for the service executing this in a transaction
	@Transactional
	public Mono<Void> transferGatesReactive(String fromId, String toId, int gatesToTransfer, RuntimeException exceptionToThrow) {
		return Mono.deferContextual(ctx -> {
		// May wish to include this check to confirm this is actually in a transaction.
		TransactionalSupport.checkForTransactionInThreadLocalStorage().map((h) -> {
			if (!h.isPresent())
				throw new RuntimeException("not in transaction!");
			return h;
		});

		AirlineGates fromAirlineGates = template.findById(AirlineGates.class).one(fromId);
		AirlineGates toAirlineGates = template.findById(AirlineGates.class).one(toId);
		toAirlineGates.gates += gatesToTransfer;
		fromAirlineGates.gates -= gatesToTransfer;
		template.save(fromAirlineGates);
		if(exceptionToThrow != null){
			throw exceptionToThrow;
		}
		return reactiveTemplate.save(toAirlineGates).then();
		});
	}

	// This does not have the @Transactional annotation therefore is not executed in a transaction
	public AirlineGates save(AirlineGates airlineGates) {
		return template.save(airlineGates);
	}

	// This does not have the @Transactional annotation therefore is not executed in a transaction
	public AirlineGates findById(String id) {
		return template.findById(AirlineGates.class).one(id);
	}
}
