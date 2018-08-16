package example.repo;

import example.model.Customer948;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer948Repository extends CrudRepository<Customer948, Long> {

	List<Customer948> findByLastName(String lastName);
}
