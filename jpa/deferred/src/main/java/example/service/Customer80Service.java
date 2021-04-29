package example.service;

import example.repo.Customer80Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer80Service {
	public Customer80Service(Customer80Repository repo) {}
}
