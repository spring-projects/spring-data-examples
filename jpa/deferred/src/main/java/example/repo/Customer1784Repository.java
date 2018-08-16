package example.repo;

import example.model.Customer1784;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1784Repository extends CrudRepository<Customer1784, Long> {

	List<Customer1784> findByLastName(String lastName);
}
