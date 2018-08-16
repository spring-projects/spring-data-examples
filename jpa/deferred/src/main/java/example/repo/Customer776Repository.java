package example.repo;

import example.model.Customer776;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer776Repository extends CrudRepository<Customer776, Long> {

	List<Customer776> findByLastName(String lastName);
}
