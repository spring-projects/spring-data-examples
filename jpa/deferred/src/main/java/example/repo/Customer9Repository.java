package example.repo;

import example.model.Customer9;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer9Repository extends CrudRepository<Customer9, Long> {

	List<Customer9> findByLastName(String lastName);
}
