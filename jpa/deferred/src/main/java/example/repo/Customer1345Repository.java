package example.repo;

import example.model.Customer1345;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1345Repository extends CrudRepository<Customer1345, Long> {

	List<Customer1345> findByLastName(String lastName);
}
