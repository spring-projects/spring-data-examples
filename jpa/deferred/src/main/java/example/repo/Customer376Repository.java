package example.repo;

import example.model.Customer376;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer376Repository extends CrudRepository<Customer376, Long> {

	List<Customer376> findByLastName(String lastName);
}
