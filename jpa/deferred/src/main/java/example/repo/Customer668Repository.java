package example.repo;

import example.model.Customer668;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer668Repository extends CrudRepository<Customer668, Long> {

	List<Customer668> findByLastName(String lastName);
}
