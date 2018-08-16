package example.repo;

import example.model.Customer1631;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1631Repository extends CrudRepository<Customer1631, Long> {

	List<Customer1631> findByLastName(String lastName);
}
