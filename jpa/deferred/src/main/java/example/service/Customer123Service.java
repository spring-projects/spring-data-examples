package example.service;

import example.repo.Customer123Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer123Service {
	public Customer123Service(Customer123Repository repo) {}
}
