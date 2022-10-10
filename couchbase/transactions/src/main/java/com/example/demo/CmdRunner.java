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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.transaction.error.TransactionSystemUnambiguousException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Components of the type CommandLineRunner are called right after the application start up. So the method *run* is
 * called as soon as the application starts.
 * 
 * @author Michael Reiche
 */
@Component
public class CmdRunner implements CommandLineRunner {

	@Autowired CouchbaseTemplate template;
	@Autowired AirlineGatesService airlineGatesService;

	@Override
	public void run(String... strings) {

		try { // remove leftovers from previous run
			template.removeById(AirlineGates.class).one("1");
		} catch (Exception e) {}
		try {
			template.removeById(AirlineGates.class).one("2");
		} catch (Exception e) {}

		AirlineGates airlineGates1 = new AirlineGates("1", "JFK", "American Airlines", Long.valueOf(200)); //1
		AirlineGates airlineGates2 = new AirlineGates("2", "JFK", "Lufthansa", Long.valueOf(200));
		AirlineGates saved1 = airlineGatesService.save(airlineGates1);
		AirlineGates saved2 = airlineGatesService.save(airlineGates2);
		AirlineGates found1 = airlineGatesService.findById(saved1.getId()); //2
		AirlineGates found2 = airlineGatesService.findById(saved2.getId());
		System.err.println("initialized airlines");
		System.err.println("  found before transferGates: " + found1);
		System.err.println("  found before transferGates: " + found2);
		System.err.println("this transferGates attempt will succeed");
		// move 50 gates from airline1 to airline2
		int gatesToTransfer=50;
		airlineGatesService.transferGates(airlineGates1.getId(), airlineGates2.getId(), gatesToTransfer, null); //3
		found1 = airlineGatesService.findById(saved1.getId());
		found2 = airlineGatesService.findById(saved2.getId());
		System.err.println("  found after  transferGates: " + found1); //4
		System.err.println("  found after  transferGates: " + found2);
		Assert.isTrue(found1.getGates().equals(airlineGates1.getGates()-gatesToTransfer), "should have transferred");
		Assert.isTrue(found2.getGates().equals(airlineGates1.getGates()+gatesToTransfer), "should have transferred");
		System.err.println("this transferGates attempt will fail");
		// attempt to move 44 gates from airline1 to airline2, but it fails.
		try {
			// 5
			airlineGatesService.transferGates(airlineGates1.getId(), airlineGates2.getId(), 44, new SimulateErrorException());
		} catch (RuntimeException rte) {
			if (!(rte instanceof TransactionSystemUnambiguousException) && rte != null
					&& rte.getCause() instanceof SimulateErrorException) {
				throw rte;
			}
			System.err.println("  got exception "+rte);
		}
		System.err.println("  found after  transferGates: " + airlineGatesService.findById(airlineGates1.getId()));
		System.err.println("  found after  transferGates: " + airlineGatesService.findById(airlineGates2.getId()));
		Assert.isTrue(found1.getGates().equals(airlineGates1.getGates()-gatesToTransfer), "should be same as previous");
		Assert.isTrue(found2.getGates().equals(airlineGates1.getGates()+gatesToTransfer), "should be same as previous");
		System.err.println("this transferGates attempt will succeed");
		try {
			// 5
			airlineGatesService.transferGatesReactive(airlineGates1.getId(), airlineGates2.getId(), 44, null).block();
		} catch (RuntimeException rte) {
			if (!(rte instanceof TransactionSystemUnambiguousException) && rte != null
					&& rte.getCause() instanceof SimulateErrorException) {
				throw rte;
			}
			System.err.println("  got exception "+rte);
		}
		System.err.println("  found after  transferGates: " + airlineGatesService.findById(airlineGates1.getId()));
		System.err.println("  found after  transferGates: " + airlineGatesService.findById(airlineGates2.getId()));
		Assert.isTrue(found1.getGates().equals(airlineGates1.getGates()-gatesToTransfer), "should have transferred");
		Assert.isTrue(found2.getGates().equals(airlineGates1.getGates()+gatesToTransfer), "should have transferred");
	}

	static class SimulateErrorException extends RuntimeException {}
}
