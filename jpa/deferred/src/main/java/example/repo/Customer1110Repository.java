package example.repo;

import example.model.Customer1110;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1110Repository extends CrudRepository<Customer1110, Long> {

	List<Customer1110> findByLastName(String lastName);
}
