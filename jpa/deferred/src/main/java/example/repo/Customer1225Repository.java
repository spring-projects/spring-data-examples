package example.repo;

import example.model.Customer1225;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1225Repository extends CrudRepository<Customer1225, Long> {

	List<Customer1225> findByLastName(String lastName);
}
