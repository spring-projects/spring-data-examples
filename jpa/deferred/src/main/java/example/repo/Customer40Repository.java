package example.repo;

import example.model.Customer40;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer40Repository extends CrudRepository<Customer40, Long> {

	List<Customer40> findByLastName(String lastName);
}
