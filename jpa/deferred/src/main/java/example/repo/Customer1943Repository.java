package example.repo;

import example.model.Customer1943;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1943Repository extends CrudRepository<Customer1943, Long> {

	List<Customer1943> findByLastName(String lastName);
}
