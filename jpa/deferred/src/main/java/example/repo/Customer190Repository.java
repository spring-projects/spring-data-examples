package example.repo;

import example.model.Customer190;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer190Repository extends CrudRepository<Customer190, Long> {

	List<Customer190> findByLastName(String lastName);
}
