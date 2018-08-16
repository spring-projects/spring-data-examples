package example.repo;

import example.model.Customer1092;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1092Repository extends CrudRepository<Customer1092, Long> {

	List<Customer1092> findByLastName(String lastName);
}
