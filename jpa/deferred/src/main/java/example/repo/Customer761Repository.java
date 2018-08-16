package example.repo;

import example.model.Customer761;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer761Repository extends CrudRepository<Customer761, Long> {

	List<Customer761> findByLastName(String lastName);
}
