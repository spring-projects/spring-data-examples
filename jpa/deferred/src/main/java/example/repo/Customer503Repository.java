package example.repo;

import example.model.Customer503;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer503Repository extends CrudRepository<Customer503, Long> {

	List<Customer503> findByLastName(String lastName);
}
