package example.service;

import example.repo.Customer411Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer411Service {
	public Customer411Service(Customer411Repository repo) {}
}
