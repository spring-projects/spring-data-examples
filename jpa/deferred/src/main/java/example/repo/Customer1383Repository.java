package example.repo;

import example.model.Customer1383;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1383Repository extends CrudRepository<Customer1383, Long> {

	List<Customer1383> findByLastName(String lastName);
}
