package example.repo;

import example.model.Customer1616;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1616Repository extends CrudRepository<Customer1616, Long> {

	List<Customer1616> findByLastName(String lastName);
}
