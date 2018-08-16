package example.repo;

import example.model.Customer1315;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1315Repository extends CrudRepository<Customer1315, Long> {

	List<Customer1315> findByLastName(String lastName);
}
