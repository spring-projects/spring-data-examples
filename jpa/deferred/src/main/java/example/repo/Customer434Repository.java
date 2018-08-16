package example.repo;

import example.model.Customer434;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer434Repository extends CrudRepository<Customer434, Long> {

	List<Customer434> findByLastName(String lastName);
}
