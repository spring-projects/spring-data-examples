package example.repo;

import example.model.Customer1866;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1866Repository extends CrudRepository<Customer1866, Long> {

	List<Customer1866> findByLastName(String lastName);
}
