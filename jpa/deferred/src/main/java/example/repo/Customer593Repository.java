package example.repo;

import example.model.Customer593;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer593Repository extends CrudRepository<Customer593, Long> {

	List<Customer593> findByLastName(String lastName);
}
