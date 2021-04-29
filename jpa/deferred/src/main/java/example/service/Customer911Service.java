package example.service;

import example.repo.Customer911Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer911Service {
	public Customer911Service(Customer911Repository repo) {}
}
