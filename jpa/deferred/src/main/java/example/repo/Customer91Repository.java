package example.repo;

import example.model.Customer91;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer91Repository extends CrudRepository<Customer91, Long> {

	List<Customer91> findByLastName(String lastName);
}
