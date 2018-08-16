package example.repo;

import example.model.Customer805;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer805Repository extends CrudRepository<Customer805, Long> {

	List<Customer805> findByLastName(String lastName);
}
