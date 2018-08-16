package example.repo;

import example.model.Customer1371;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1371Repository extends CrudRepository<Customer1371, Long> {

	List<Customer1371> findByLastName(String lastName);
}
