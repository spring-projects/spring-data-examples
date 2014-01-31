package org.springframework.data.jpa.showcase.core;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Oliver Gierke
 */
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Customer customer;

	@Temporal(TemporalType.DATE)
	private Date expiryDate;

	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
}
