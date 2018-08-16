package example.repo;

import example.model.Customer276;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer276Repository extends CrudRepository<Customer276, Long> {

	List<Customer276> findByLastName(String lastName);
}
