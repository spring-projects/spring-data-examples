package example.repo;

import example.model.Customer114;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer114Repository extends CrudRepository<Customer114, Long> {

	List<Customer114> findByLastName(String lastName);
}
