package example.repo;

import example.model.Customer354;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer354Repository extends CrudRepository<Customer354, Long> {

	List<Customer354> findByLastName(String lastName);
}
