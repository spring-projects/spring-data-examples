package example.repo;

import example.model.Customer979;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer979Repository extends CrudRepository<Customer979, Long> {

	List<Customer979> findByLastName(String lastName);
}
