package example.repo;

import example.model.Customer1320;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1320Repository extends CrudRepository<Customer1320, Long> {

	List<Customer1320> findByLastName(String lastName);
}
