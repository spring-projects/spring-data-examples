package example.repo;

import example.model.Customer1250;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1250Repository extends CrudRepository<Customer1250, Long> {

	List<Customer1250> findByLastName(String lastName);
}
