package example.repo;

import example.model.Customer282;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer282Repository extends CrudRepository<Customer282, Long> {

	List<Customer282> findByLastName(String lastName);
}
