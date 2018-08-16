package example.repo;

import example.model.Customer160;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer160Repository extends CrudRepository<Customer160, Long> {

	List<Customer160> findByLastName(String lastName);
}
