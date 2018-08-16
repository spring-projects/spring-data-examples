package example.repo;

import example.model.Customer733;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer733Repository extends CrudRepository<Customer733, Long> {

	List<Customer733> findByLastName(String lastName);
}
