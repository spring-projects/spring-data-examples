package example.repo;

import example.model.Customer1446;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1446Repository extends CrudRepository<Customer1446, Long> {

	List<Customer1446> findByLastName(String lastName);
}
