package example.repo;

import example.model.Customer335;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer335Repository extends CrudRepository<Customer335, Long> {

	List<Customer335> findByLastName(String lastName);
}
