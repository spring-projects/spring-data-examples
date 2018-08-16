package example.repo;

import example.model.Customer473;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer473Repository extends CrudRepository<Customer473, Long> {

	List<Customer473> findByLastName(String lastName);
}
