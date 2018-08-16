package example.repo;

import example.model.Customer1357;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1357Repository extends CrudRepository<Customer1357, Long> {

	List<Customer1357> findByLastName(String lastName);
}
