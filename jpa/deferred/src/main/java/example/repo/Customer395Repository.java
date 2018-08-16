package example.repo;

import example.model.Customer395;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer395Repository extends CrudRepository<Customer395, Long> {

	List<Customer395> findByLastName(String lastName);
}
