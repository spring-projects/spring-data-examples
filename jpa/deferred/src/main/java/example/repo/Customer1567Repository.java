package example.repo;

import example.model.Customer1567;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1567Repository extends CrudRepository<Customer1567, Long> {

	List<Customer1567> findByLastName(String lastName);
}
