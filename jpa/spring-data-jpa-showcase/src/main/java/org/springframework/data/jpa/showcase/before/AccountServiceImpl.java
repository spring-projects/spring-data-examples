package org.springframework.data.jpa.showcase.before;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.showcase.core.Account;
import org.springframework.data.jpa.showcase.core.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Plain JPA implementation of {@link AccountService}.
 * 
 * @author Oliver Gierke
 */
@Repository
@Transactional(readOnly = true)
class AccountServiceImpl implements AccountService {

	@PersistenceContext
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.showcase.before.AccountService#save(org.springframework.data.jpa.showcase.core.Account)
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
	 * @see org.springframework.data.jpa.showcase.before.AccountService#findByCustomer(org.springframework.data.jpa.showcase.core.Customer)
	 */
	@Override
	public List<Account> findByCustomer(Customer customer) {

		TypedQuery<Account> query = em.createQuery("select a from Account a where a.customer = ?1", Account.class);
		query.setParameter(1, customer);

		return query.getResultList();
	}
}
