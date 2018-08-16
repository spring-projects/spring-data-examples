package example.repo;

import example.model.Customer1135;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1135Repository extends CrudRepository<Customer1135, Long> {

	List<Customer1135> findByLastName(String lastName);
}
