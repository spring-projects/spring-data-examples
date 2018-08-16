package example.repo;

import example.model.Customer502;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer502Repository extends CrudRepository<Customer502, Long> {

	List<Customer502> findByLastName(String lastName);
}
