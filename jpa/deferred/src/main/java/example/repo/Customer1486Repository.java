package example.repo;

import example.model.Customer1486;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1486Repository extends CrudRepository<Customer1486, Long> {

	List<Customer1486> findByLastName(String lastName);
}
