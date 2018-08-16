package example.repo;

import example.model.Customer1630;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1630Repository extends CrudRepository<Customer1630, Long> {

	List<Customer1630> findByLastName(String lastName);
}
