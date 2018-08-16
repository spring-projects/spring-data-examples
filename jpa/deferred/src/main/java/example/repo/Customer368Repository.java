package example.repo;

import example.model.Customer368;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer368Repository extends CrudRepository<Customer368, Long> {

	List<Customer368> findByLastName(String lastName);
}
