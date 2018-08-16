package example.repo;

import example.model.Customer195;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer195Repository extends CrudRepository<Customer195, Long> {

	List<Customer195> findByLastName(String lastName);
}
