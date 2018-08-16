package example.repo;

import example.model.Customer306;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer306Repository extends CrudRepository<Customer306, Long> {

	List<Customer306> findByLastName(String lastName);
}
