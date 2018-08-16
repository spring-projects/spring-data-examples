package example.repo;

import example.model.Customer1897;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1897Repository extends CrudRepository<Customer1897, Long> {

	List<Customer1897> findByLastName(String lastName);
}
