package example.repo;

import example.model.Customer1773;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1773Repository extends CrudRepository<Customer1773, Long> {

	List<Customer1773> findByLastName(String lastName);
}
