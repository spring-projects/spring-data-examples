package example.repo;

import example.model.Customer798;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer798Repository extends CrudRepository<Customer798, Long> {

	List<Customer798> findByLastName(String lastName);
}
