package example.service;

import example.repo.Customer404Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer404Service {
	public Customer404Service(Customer404Repository repo) {}
}
