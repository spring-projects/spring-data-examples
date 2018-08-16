package example.repo;

import example.model.Customer1791;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1791Repository extends CrudRepository<Customer1791, Long> {

	List<Customer1791> findByLastName(String lastName);
}
