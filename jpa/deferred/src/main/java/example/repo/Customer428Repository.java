package example.repo;

import example.model.Customer428;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer428Repository extends CrudRepository<Customer428, Long> {

	List<Customer428> findByLastName(String lastName);
}
