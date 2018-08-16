package example.repo;

import example.model.Customer1476;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1476Repository extends CrudRepository<Customer1476, Long> {

	List<Customer1476> findByLastName(String lastName);
}
