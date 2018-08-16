package example.repo;

import example.model.Customer1500;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1500Repository extends CrudRepository<Customer1500, Long> {

	List<Customer1500> findByLastName(String lastName);
}
