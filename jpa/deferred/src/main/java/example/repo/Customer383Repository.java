package example.repo;

import example.model.Customer383;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer383Repository extends CrudRepository<Customer383, Long> {

	List<Customer383> findByLastName(String lastName);
}
