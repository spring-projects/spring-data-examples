package example.repo;

import example.model.Customer855;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer855Repository extends CrudRepository<Customer855, Long> {

	List<Customer855> findByLastName(String lastName);
}
