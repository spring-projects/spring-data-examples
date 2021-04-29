package example.service;

import example.repo.Customer1337Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer1337Service {
	public Customer1337Service(Customer1337Repository repo) {}
}
