package example.repo;

import example.model.Customer1326;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1326Repository extends CrudRepository<Customer1326, Long> {

	List<Customer1326> findByLastName(String lastName);
}
