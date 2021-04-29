package example.service;

import example.repo.Customer1Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer1Service {
	public Customer1Service(Customer1Repository repo) {}
}
