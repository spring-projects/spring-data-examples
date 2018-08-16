package example.repo;

import example.model.Customer116;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer116Repository extends CrudRepository<Customer116, Long> {

	List<Customer116> findByLastName(String lastName);
}
