package example.repo;

import example.model.Customer1635;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1635Repository extends CrudRepository<Customer1635, Long> {

	List<Customer1635> findByLastName(String lastName);
}
