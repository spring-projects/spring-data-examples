package example.service;

import example.repo.Customer100Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer100Service {
	public Customer100Service(Customer100Repository repo) {}
}
