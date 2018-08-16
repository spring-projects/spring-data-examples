package example.repo;

import example.model.Customer1518;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1518Repository extends CrudRepository<Customer1518, Long> {

	List<Customer1518> findByLastName(String lastName);
}
