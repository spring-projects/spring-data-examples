package example.repo;

import example.model.Customer1376;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1376Repository extends CrudRepository<Customer1376, Long> {

	List<Customer1376> findByLastName(String lastName);
}
