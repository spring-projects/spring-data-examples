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

import example.springdata.jpa.showcase.core.Customer;

/**
 * Plain JPA implementation of {@link CustomerService}.
 * 
 * @author Oliver Gierke
 */
@Repository
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@PersistenceContext private EntityManager em;

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.showcase.before.CustomerService#findById(java.lang.Long)
	 */
	@Override
	public Customer findById(Long id) {
		return em.find(Customer.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.showcase.before.CustomerService#findAll()
	 */
	@Override
	public List<Customer> findAll() {
		return em.createQuery("select c from Customer c", Customer.class).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see example.springdata.jpa.showcase.before.CustomerService#findAll(int, int)
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
	 * @see example.springdata.jpa.showcase.before.CustomerService#save(example.springdata.jpa.showcase.core.Customer)
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
	 * @see example.springdata.jpa.showcase.before.CustomerService#findByLastname(java.lang.String, int, int)
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
