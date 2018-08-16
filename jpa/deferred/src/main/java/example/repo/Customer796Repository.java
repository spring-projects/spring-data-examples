package example.repo;

import example.model.Customer796;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer796Repository extends CrudRepository<Customer796, Long> {

	List<Customer796> findByLastName(String lastName);
}
