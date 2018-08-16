package example.repo;

import example.model.Customer283;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer283Repository extends CrudRepository<Customer283, Long> {

	List<Customer283> findByLastName(String lastName);
}
