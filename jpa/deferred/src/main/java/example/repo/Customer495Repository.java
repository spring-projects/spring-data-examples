package example.repo;

import example.model.Customer495;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer495Repository extends CrudRepository<Customer495, Long> {

	List<Customer495> findByLastName(String lastName);
}
