package example.repo;

import example.model.Customer1624;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1624Repository extends CrudRepository<Customer1624, Long> {

	List<Customer1624> findByLastName(String lastName);
}
