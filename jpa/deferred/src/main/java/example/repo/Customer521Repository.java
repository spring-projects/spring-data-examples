package example.repo;

import example.model.Customer521;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer521Repository extends CrudRepository<Customer521, Long> {

	List<Customer521> findByLastName(String lastName);
}
