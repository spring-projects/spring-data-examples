package example.repo;

import example.model.Customer1758;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1758Repository extends CrudRepository<Customer1758, Long> {

	List<Customer1758> findByLastName(String lastName);
}
