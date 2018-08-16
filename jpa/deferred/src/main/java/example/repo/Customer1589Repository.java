package example.repo;

import example.model.Customer1589;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1589Repository extends CrudRepository<Customer1589, Long> {

	List<Customer1589> findByLastName(String lastName);
}
