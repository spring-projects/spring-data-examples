package example.service;

import example.repo.Customer101Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer101Service {
	public Customer101Service(Customer101Repository repo) {}
}
