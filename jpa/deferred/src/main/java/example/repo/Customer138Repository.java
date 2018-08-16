package example.repo;

import example.model.Customer138;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer138Repository extends CrudRepository<Customer138, Long> {

	List<Customer138> findByLastName(String lastName);
}
