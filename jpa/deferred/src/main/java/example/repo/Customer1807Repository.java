package example.repo;

import example.model.Customer1807;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1807Repository extends CrudRepository<Customer1807, Long> {

	List<Customer1807> findByLastName(String lastName);
}
