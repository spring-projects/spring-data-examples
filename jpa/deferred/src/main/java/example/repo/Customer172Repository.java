package example.repo;

import example.model.Customer172;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer172Repository extends CrudRepository<Customer172, Long> {

	List<Customer172> findByLastName(String lastName);
}
