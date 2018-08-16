package example.repo;

import example.model.Customer244;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer244Repository extends CrudRepository<Customer244, Long> {

	List<Customer244> findByLastName(String lastName);
}
