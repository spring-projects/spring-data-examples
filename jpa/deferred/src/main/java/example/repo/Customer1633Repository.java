package example.repo;

import example.model.Customer1633;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1633Repository extends CrudRepository<Customer1633, Long> {

	List<Customer1633> findByLastName(String lastName);
}
