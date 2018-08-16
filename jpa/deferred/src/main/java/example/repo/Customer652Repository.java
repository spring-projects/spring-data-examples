package example.repo;

import example.model.Customer652;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer652Repository extends CrudRepository<Customer652, Long> {

	List<Customer652> findByLastName(String lastName);
}
