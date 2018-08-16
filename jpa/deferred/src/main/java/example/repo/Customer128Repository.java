package example.repo;

import example.model.Customer128;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer128Repository extends CrudRepository<Customer128, Long> {

	List<Customer128> findByLastName(String lastName);
}
