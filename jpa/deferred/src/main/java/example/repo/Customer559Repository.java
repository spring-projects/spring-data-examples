package example.repo;

import example.model.Customer559;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer559Repository extends CrudRepository<Customer559, Long> {

	List<Customer559> findByLastName(String lastName);
}
