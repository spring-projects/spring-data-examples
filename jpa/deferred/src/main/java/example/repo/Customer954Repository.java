package example.repo;

import example.model.Customer954;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer954Repository extends CrudRepository<Customer954, Long> {

	List<Customer954> findByLastName(String lastName);
}
