package example.repo;

import example.model.Customer916;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer916Repository extends CrudRepository<Customer916, Long> {

	List<Customer916> findByLastName(String lastName);
}
