package example.repo;

import example.model.Customer768;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer768Repository extends CrudRepository<Customer768, Long> {

	List<Customer768> findByLastName(String lastName);
}
