package example.repo;

import example.model.Customer232;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer232Repository extends CrudRepository<Customer232, Long> {

	List<Customer232> findByLastName(String lastName);
}
