package example.repo;

import example.model.Customer826;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer826Repository extends CrudRepository<Customer826, Long> {

	List<Customer826> findByLastName(String lastName);
}
