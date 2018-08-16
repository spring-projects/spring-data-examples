package example.repo;

import example.model.Customer359;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer359Repository extends CrudRepository<Customer359, Long> {

	List<Customer359> findByLastName(String lastName);
}
