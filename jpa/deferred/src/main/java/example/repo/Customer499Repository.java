package example.repo;

import example.model.Customer499;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer499Repository extends CrudRepository<Customer499, Long> {

	List<Customer499> findByLastName(String lastName);
}
