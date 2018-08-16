package example.repo;

import example.model.Customer147;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer147Repository extends CrudRepository<Customer147, Long> {

	List<Customer147> findByLastName(String lastName);
}
