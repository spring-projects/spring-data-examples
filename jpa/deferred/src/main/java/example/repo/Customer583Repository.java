package example.repo;

import example.model.Customer583;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer583Repository extends CrudRepository<Customer583, Long> {

	List<Customer583> findByLastName(String lastName);
}
