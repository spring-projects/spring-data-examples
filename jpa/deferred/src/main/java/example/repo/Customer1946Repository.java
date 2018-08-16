package example.repo;

import example.model.Customer1946;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1946Repository extends CrudRepository<Customer1946, Long> {

	List<Customer1946> findByLastName(String lastName);
}
