package example.repo;

import example.model.Customer754;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer754Repository extends CrudRepository<Customer754, Long> {

	List<Customer754> findByLastName(String lastName);
}
