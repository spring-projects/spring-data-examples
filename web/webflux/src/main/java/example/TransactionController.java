/*
 * Copyright 2018 the original author or authors.
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
package example;

import example.jpa.BankAccount;
import example.jpa.BankAccountRepository;
import example.mongo.Person;
import example.mongo.ReactivePersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import org.springframework.data.jpa.support.JpaExecutor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller using a MongoDB {@link org.springframework.data.repository.reactive.ReactiveCrudRepository} and
 * {@link JpaExecutor} to transactionally update objects.
 *
 * @author Mark Paluch
 */
@RestController
@RequiredArgsConstructor
public class TransactionController {

	final ReactivePersonRepository personRepository;
	final BankAccountRepository bankAccountRepository;
	final JpaExecutor jpaExecutor;

	@PutMapping("transactions")
	Mono<Void> storeTransaction(@RequestBody Transaction transaction) {

		return personRepository.findByFirstnameIgnoringCase(transaction.getSender())
				.zipWith(personRepository.findByFirstnameIgnoringCase(transaction.getRecipient())) //
				.switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find sender/recipient!"))) //
				.flatMap(it -> {

					Person owner = it.getT1();
					Person recipient = it.getT2();

					return jpaExecutor.transactional().doInTransaction(bankAccountRepository, repo -> {

						BankAccount from = repo.findByOwner(owner.getId());
						BankAccount to = repo.findByOwner(recipient.getId());

						if (from.getBalance().compareTo(transaction.getAmount()) < 0) {
							throw new IllegalStateException("Sender account does not have enough funding!");
						}

						from.setBalance(from.getBalance().subtract(transaction.getAmount()));
						to.setBalance(to.getBalance().add(transaction.getAmount()));

						repo.save(from);
						repo.save(to);

						return null;
					});
				}) //
				.then();
	}

	@Value
	static class Transaction {

		String sender;
		String recipient;
		BigDecimal amount;
	}

}
