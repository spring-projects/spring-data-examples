package example.repo;

import example.model.Customer1706;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1706Repository extends CrudRepository<Customer1706, Long> {

	List<Customer1706> findByLastName(String lastName);
}
