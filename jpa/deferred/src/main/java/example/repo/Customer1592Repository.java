package example.repo;

import example.model.Customer1592;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1592Repository extends CrudRepository<Customer1592, Long> {

	List<Customer1592> findByLastName(String lastName);
}
