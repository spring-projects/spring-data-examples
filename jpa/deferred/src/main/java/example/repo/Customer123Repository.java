package example.repo;

import example.model.Customer123;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer123Repository extends CrudRepository<Customer123, Long> {

	List<Customer123> findByLastName(String lastName);
}
