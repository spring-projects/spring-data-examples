package example.repo;

import example.model.Customer176;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer176Repository extends CrudRepository<Customer176, Long> {

	List<Customer176> findByLastName(String lastName);
}
