package example.repo;

import example.model.Customer1098;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1098Repository extends CrudRepository<Customer1098, Long> {

	List<Customer1098> findByLastName(String lastName);
}
