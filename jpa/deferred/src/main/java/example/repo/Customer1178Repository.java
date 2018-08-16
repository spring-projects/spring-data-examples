package example.repo;

import example.model.Customer1178;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1178Repository extends CrudRepository<Customer1178, Long> {

	List<Customer1178> findByLastName(String lastName);
}
