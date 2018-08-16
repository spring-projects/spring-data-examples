package example.repo;

import example.model.Customer1190;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1190Repository extends CrudRepository<Customer1190, Long> {

	List<Customer1190> findByLastName(String lastName);
}
