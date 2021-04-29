package example.service;

import example.repo.Customer365Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer365Service {
	public Customer365Service(Customer365Repository repo) {}
}
