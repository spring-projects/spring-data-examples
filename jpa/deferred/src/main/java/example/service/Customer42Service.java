package example.service;

import example.repo.Customer42Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer42Service {
	public Customer42Service(Customer42Repository repo) {}
}
