package example.repo;

import example.model.Customer1375;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1375Repository extends CrudRepository<Customer1375, Long> {

	List<Customer1375> findByLastName(String lastName);
}
