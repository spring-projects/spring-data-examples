package example.repo;

import example.model.Customer245;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer245Repository extends CrudRepository<Customer245, Long> {

	List<Customer245> findByLastName(String lastName);
}
