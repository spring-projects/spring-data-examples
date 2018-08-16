package example.repo;

import example.model.Customer491;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer491Repository extends CrudRepository<Customer491, Long> {

	List<Customer491> findByLastName(String lastName);
}
