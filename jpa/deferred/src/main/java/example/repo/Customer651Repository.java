package example.repo;

import example.model.Customer651;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer651Repository extends CrudRepository<Customer651, Long> {

	List<Customer651> findByLastName(String lastName);
}
