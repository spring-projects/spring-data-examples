package example.service;

import example.repo.Customer401Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer401Service {
	public Customer401Service(Customer401Repository repo) {}
}
