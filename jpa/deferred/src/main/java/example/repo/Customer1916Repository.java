package example.repo;

import example.model.Customer1916;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1916Repository extends CrudRepository<Customer1916, Long> {

	List<Customer1916> findByLastName(String lastName);
}
