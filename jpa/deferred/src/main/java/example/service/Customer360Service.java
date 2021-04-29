package example.service;

import example.repo.Customer360Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer360Service {
	public Customer360Service(Customer360Repository repo) {}
}
