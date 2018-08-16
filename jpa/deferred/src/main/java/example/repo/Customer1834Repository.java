package example.repo;

import example.model.Customer1834;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1834Repository extends CrudRepository<Customer1834, Long> {

	List<Customer1834> findByLastName(String lastName);
}
