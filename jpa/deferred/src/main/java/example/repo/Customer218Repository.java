package example.repo;

import example.model.Customer218;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer218Repository extends CrudRepository<Customer218, Long> {

	List<Customer218> findByLastName(String lastName);
}
