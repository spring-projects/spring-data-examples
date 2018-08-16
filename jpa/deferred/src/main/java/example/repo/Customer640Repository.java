package example.repo;

import example.model.Customer640;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer640Repository extends CrudRepository<Customer640, Long> {

	List<Customer640> findByLastName(String lastName);
}
