package example.repo;

import example.model.Customer1761;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1761Repository extends CrudRepository<Customer1761, Long> {

	List<Customer1761> findByLastName(String lastName);
}
