package example.repo;

import example.model.Customer1893;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1893Repository extends CrudRepository<Customer1893, Long> {

	List<Customer1893> findByLastName(String lastName);
}
