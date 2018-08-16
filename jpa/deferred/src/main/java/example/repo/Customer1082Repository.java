package example.repo;

import example.model.Customer1082;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1082Repository extends CrudRepository<Customer1082, Long> {

	List<Customer1082> findByLastName(String lastName);
}
