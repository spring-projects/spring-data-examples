package example.repo;

import example.model.Customer476;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer476Repository extends CrudRepository<Customer476, Long> {

	List<Customer476> findByLastName(String lastName);
}
