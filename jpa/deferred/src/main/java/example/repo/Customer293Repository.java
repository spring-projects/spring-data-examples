package example.repo;

import example.model.Customer293;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer293Repository extends CrudRepository<Customer293, Long> {

	List<Customer293> findByLastName(String lastName);
}
