package example.repo;

import example.model.Customer852;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer852Repository extends CrudRepository<Customer852, Long> {

	List<Customer852> findByLastName(String lastName);
}
