/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.showcase.snippets;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import example.springdata.jpa.showcase.core.Account;

/**
 * @author Oliver Gierke
 */
@Repository
class AccountRepositoryImpl implements AccountRepositoryCustom {

	private final EntityManager em;

	@Autowired
	public AccountRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.showcase.snippets.AccountRepositoryCustom#removedExpiredAccounts(org.joda.time.LocalDate)
	 */
	@Override
	public void removedExpiredAccounts(LocalDate reference) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> query = cb.createQuery(Account.class);
		Root<Account> account = query.from(Account.class);

		query.where(cb.lessThan(account.get("expiryDate").as(Date.class), reference.toDateTimeAtStartOfDay().toDate()));

		for (Account each : em.createQuery(query).getResultList()) {
			em.remove(each);
		}
	}
}
