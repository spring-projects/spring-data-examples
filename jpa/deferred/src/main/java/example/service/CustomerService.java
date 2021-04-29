package example.service;

import example.repo.CustomerRepository;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	public CustomerService(CustomerRepository repo) {}
}
