package example.repo;

import example.model.Customer713;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer713Repository extends CrudRepository<Customer713, Long> {

	List<Customer713> findByLastName(String lastName);
}
