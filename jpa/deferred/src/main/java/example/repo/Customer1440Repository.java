package example.repo;

import example.model.Customer1440;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1440Repository extends CrudRepository<Customer1440, Long> {

	List<Customer1440> findByLastName(String lastName);
}
