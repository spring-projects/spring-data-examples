package example.repo;

import example.model.Customer1461;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1461Repository extends CrudRepository<Customer1461, Long> {

	List<Customer1461> findByLastName(String lastName);
}
