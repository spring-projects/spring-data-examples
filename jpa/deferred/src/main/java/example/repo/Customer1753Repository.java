package example.repo;

import example.model.Customer1753;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1753Repository extends CrudRepository<Customer1753, Long> {

	List<Customer1753> findByLastName(String lastName);
}
