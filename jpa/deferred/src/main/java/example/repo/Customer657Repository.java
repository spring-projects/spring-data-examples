package example.repo;

import example.model.Customer657;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer657Repository extends CrudRepository<Customer657, Long> {

	List<Customer657> findByLastName(String lastName);
}
