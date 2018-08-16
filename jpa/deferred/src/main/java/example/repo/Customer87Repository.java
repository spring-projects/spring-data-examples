package example.repo;

import example.model.Customer87;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer87Repository extends CrudRepository<Customer87, Long> {

	List<Customer87> findByLastName(String lastName);
}
