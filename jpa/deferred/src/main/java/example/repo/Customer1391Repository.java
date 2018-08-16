package example.repo;

import example.model.Customer1391;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1391Repository extends CrudRepository<Customer1391, Long> {

	List<Customer1391> findByLastName(String lastName);
}
