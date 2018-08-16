package example.repo;

import example.model.Customer1472;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1472Repository extends CrudRepository<Customer1472, Long> {

	List<Customer1472> findByLastName(String lastName);
}
