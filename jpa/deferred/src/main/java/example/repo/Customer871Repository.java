package example.repo;

import example.model.Customer871;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer871Repository extends CrudRepository<Customer871, Long> {

	List<Customer871> findByLastName(String lastName);
}
