package org.springframework.data.jpa.showcase.before;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.showcase.core.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Plain JPA implementation of {@link CustomerService}.
 * 
 * @author Oliver Gierke
 */
@Repository
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@PersistenceContext
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.showcase.before.CustomerService#findById(java.lang.Long)
	 */
	@Override
	public Customer findById(Long id) {
		return em.find(Customer.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.showcase.before.CustomerService#findAll()
	 */
	@Override
	public List<Customer> findAll() {
		return em.createQuery("select c from Customer c", Customer.class).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.showcase.before.CustomerService#findAll(int, int)
	 */
	@Override
	public List<Customer> findAll(int page, int pageSize) {

		TypedQuery<Customer> query = em.createQuery("select c from Customer c", Customer.class);

		query.setFirstResult(page * pageSize);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.showcase.before.CustomerService#save(org.springframework.data.jpa.showcase.core.Customer)
	 */
	@Override
	@Transactional
	public Customer save(Customer customer) {

		// Is new?
		if (customer.getId() == null) {
			em.persist(customer);
			return customer;
		} else {
			return em.merge(customer);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.showcase.before.CustomerService#findByLastname(java.lang.String, int, int)
	 */
	@Override
	public List<Customer> findByLastname(String lastname, int page, int pageSize) {

		TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.lastname = ?1", Customer.class);

		query.setParameter(1, lastname);
		query.setFirstResult(page * pageSize);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}
}
