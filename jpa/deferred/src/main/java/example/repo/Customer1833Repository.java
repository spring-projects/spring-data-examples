package example.repo;

import example.model.Customer1833;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1833Repository extends CrudRepository<Customer1833, Long> {

	List<Customer1833> findByLastName(String lastName);
}
