package example.repo;

import example.model.Customer1148;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1148Repository extends CrudRepository<Customer1148, Long> {

	List<Customer1148> findByLastName(String lastName);
}
