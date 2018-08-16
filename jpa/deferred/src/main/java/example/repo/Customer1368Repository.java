package example.repo;

import example.model.Customer1368;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1368Repository extends CrudRepository<Customer1368, Long> {

	List<Customer1368> findByLastName(String lastName);
}
