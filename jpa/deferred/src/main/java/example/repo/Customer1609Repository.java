package example.repo;

import example.model.Customer1609;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1609Repository extends CrudRepository<Customer1609, Long> {

	List<Customer1609> findByLastName(String lastName);
}
