package example.repo;

import example.model.Customer1088;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1088Repository extends CrudRepository<Customer1088, Long> {

	List<Customer1088> findByLastName(String lastName);
}
