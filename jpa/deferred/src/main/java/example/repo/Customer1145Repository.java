package example.repo;

import example.model.Customer1145;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1145Repository extends CrudRepository<Customer1145, Long> {

	List<Customer1145> findByLastName(String lastName);
}
