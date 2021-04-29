package example.service;

import example.repo.Customer2Repository;

import org.springframework.stereotype.Service;

@Service
public class Customer2Service {
	public Customer2Service(Customer2Repository repo) {}
}
