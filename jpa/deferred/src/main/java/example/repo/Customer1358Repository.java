package example.repo;

import example.model.Customer1358;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1358Repository extends CrudRepository<Customer1358, Long> {

	List<Customer1358> findByLastName(String lastName);
}
