package example.repo;

import example.model.Customer681;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer681Repository extends CrudRepository<Customer681, Long> {

	List<Customer681> findByLastName(String lastName);
}
