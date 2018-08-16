package example.repo;

import example.model.Customer24;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer24Repository extends CrudRepository<Customer24, Long> {

	List<Customer24> findByLastName(String lastName);
}
