package example.repo;

import example.model.Customer940;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer940Repository extends CrudRepository<Customer940, Long> {

	List<Customer940> findByLastName(String lastName);
}
