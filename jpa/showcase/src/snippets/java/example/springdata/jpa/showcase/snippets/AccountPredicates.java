/*
 * Copyright 2011-2021 the original author or authors.
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
package example.springdata.jpa.showcase.snippets;

import example.springdata.jpa.showcase.core.Account;
import example.springdata.jpa.showcase.core.QAccount;

import java.sql.Date;
import java.time.LocalDate;

import com.querydsl.core.types.dsl.BooleanExpression;

/**
 * Predicates for {@link Account}s.
 *
 * @author Oliver Gierke
 */
public class AccountPredicates {

	private static QAccount account = QAccount.account;

	public static BooleanExpression isExpired() {
		return expiresBefore(LocalDate.now());
	}

	public static BooleanExpression expiresBefore(LocalDate date) {
		return account.expiryDate.before(Date.valueOf(date));
	}
}
