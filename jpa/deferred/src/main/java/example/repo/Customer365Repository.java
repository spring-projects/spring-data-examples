package example.repo;

import example.model.Customer365;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer365Repository extends CrudRepository<Customer365, Long> {

	List<Customer365> findByLastName(String lastName);
}
