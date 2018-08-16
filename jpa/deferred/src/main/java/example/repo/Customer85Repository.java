package example.repo;

import example.model.Customer85;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer85Repository extends CrudRepository<Customer85, Long> {

	List<Customer85> findByLastName(String lastName);
}
