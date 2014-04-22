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
package example.springdata.jpa.showcase.before;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import example.springdata.jpa.showcase.core.Account;
import example.springdata.jpa.showcase.core.Customer;

/**
 * Plain JPA implementation of {@link AccountService}.
 * 
 * @author Oliver Gierke
 */
@Repository
@Transactional(readOnly = true)
class AccountServiceImpl implements AccountService {

	@PersistenceContext private EntityManager em;

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.showcase.before.AccountService#save(example.springdata.jpa.showcase.core.Account)
	 */
	@Override
	@Transactional
	public Account save(Account account) {

		if (account.getId() == null) {
			em.persist(account);
			return account;
		} else {
			return em.merge(account);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.showcase.before.AccountService#findByCustomer(example.springdata.jpa.showcase.core.Customer)
	 */
	@Override
	public List<Account> findByCustomer(Customer customer) {

		TypedQuery<Account> query = em.createQuery("select a from Account a where a.customer = ?1", Account.class);
		query.setParameter(1, customer);

		return query.getResultList();
	}
}
