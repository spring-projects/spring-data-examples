package example.repo;

import example.model.Customer1014;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1014Repository extends CrudRepository<Customer1014, Long> {

	List<Customer1014> findByLastName(String lastName);
}
