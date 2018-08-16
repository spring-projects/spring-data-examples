package example.repo;

import example.model.Customer1276;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1276Repository extends CrudRepository<Customer1276, Long> {

	List<Customer1276> findByLastName(String lastName);
}
