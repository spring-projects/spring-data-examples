package example.repo;

import example.model.Customer860;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer860Repository extends CrudRepository<Customer860, Long> {

	List<Customer860> findByLastName(String lastName);
}
