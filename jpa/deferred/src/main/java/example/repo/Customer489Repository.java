package example.repo;

import example.model.Customer489;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer489Repository extends CrudRepository<Customer489, Long> {

	List<Customer489> findByLastName(String lastName);
}
