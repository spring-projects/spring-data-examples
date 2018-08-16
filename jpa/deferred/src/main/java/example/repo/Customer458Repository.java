package example.repo;

import example.model.Customer458;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer458Repository extends CrudRepository<Customer458, Long> {

	List<Customer458> findByLastName(String lastName);
}
