package example.repo;

import example.model.Customer1604;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1604Repository extends CrudRepository<Customer1604, Long> {

	List<Customer1604> findByLastName(String lastName);
}
